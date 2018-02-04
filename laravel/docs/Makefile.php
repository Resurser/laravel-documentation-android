<?php

require_once __DIR__ . '/vendor/autoload.php';

class Handler
{
    /**
     * @var \Illuminate\Filesystem\Filesystem
     */
    protected $files;

    /**
     * @var ParsedownExtra
     */
    protected $parsedown;

    /**
     * @var array
     */
    protected $branches = [
        '4.0',
        '4.1',
        '4.2',
        '5.0',
        '5.1',
        '5.2',
        '5.3',
        '5.4',
        '5.5',
        '5.6',
        'master',
    ];

    /**
     * @return void
     * @throws Exception
     */
    public function __construct()
    {
        $this->files = new \Illuminate\Filesystem\Filesystem();
        $this->parsedown = new ParsedownExtra();
    }

    /**
     * @return void
     */
    public function handle()
    {
        $markdownDirectory = dirname(__FILE__);
        $publicDirectory = $markdownDirectory . '/../public/docs';

        foreach ($this->branches as $branch) {
            $branchDirectory = $markdownDirectory . '/laravel/' . $branch;
            $htmlDirectory = $publicDirectory . '/' . $branch;

            if ( ! $this->files->isDirectory($htmlDirectory)) {
                $this->files->makeDirectory($htmlDirectory);
            }

            /** @var SplFileInfo $fileInfo */
            foreach ($this->files->allFiles($branchDirectory) as $fileInfo) {
                try {
                    $getContents = $this->files->get($fileInfo->getRealPath());
                    $htmlContents = $this->parsedown->text($getContents);
                    $htmlContents = str_replace('{{version}}', $branch, $htmlContents);

                    if (preg_match_all('/href=["\'](.+?)["\']/', $htmlContents, $hrefs)) {
                        for ($i = 0; $i < count($hrefs[0]); $i++) {
                            $uri = $hrefs[1][$i];
                            if ( ! starts_with($uri, ['file:///android_asset/', '//', 'http://', 'https://', '_', '#'])) {
                                $url = $uri;
                                if (preg_match('/(.*)#/', $uri, $matches)) {
                                    $url = $matches[1];
                                }

                                if (starts_with($url, "/docs/{$branch}/")) {
                                    $android_asset = $url;
                                    if ( ! pathinfo($android_asset, PATHINFO_EXTENSION)) {
                                        $android_asset .= '.html';
                                    }

                                    $android_asset = str_replace_first("/docs/{$branch}/", 'file:///android_asset/', $android_asset);
                                    $url = str_replace('\\', '/', $android_asset);
                                }

                                $htmlContents = str_replace('href="' . $uri . '"', 'href="' . $url . '"', $htmlContents);
                            }
                        }
                    }

                    $getBasename = $fileInfo->getBasename('.md');
                    $pathToFile = $htmlDirectory . '/' . $getBasename . '.html';
                    $this->files->put($pathToFile, $htmlContents);
                } catch (Exception $ex) {
                    print "An error occurred: " . $ex->getMessage() . PHP_EOL;
                }
            }
        }

        $this->onNavigationItemSelected()
            ->onDocumentationItemSelected()
            ->onActivityDrawer();
    }

    /**
     * @return array
     * @throws \Illuminate\Contracts\Filesystem\FileNotFoundException
     */
    public function allDocumentation()
    {
        $allDocumentation = [];

        $markdownDirectory = dirname(__FILE__);
        foreach ($this->branches as $branch) {
            $documentation = [];

            $branchDirectory = $markdownDirectory . '/laravel/' . $branch;
            $pathToFile = $branchDirectory . '/documentation.md';
            $markdownContents = $this->files->get($pathToFile);
            $lines = explode(PHP_EOL, $markdownContents);

            $heading = null;
            foreach ($lines as $line) {
                $document = null;
                $uri = null;

                if (preg_match('/^- (.*)/', $line, $headings)) {
                    $heading = $headings[1];
                    if (preg_match('/^## (.*)/', $heading, $prefixes)) {
                        $heading = $prefixes[1];
                    }
                } elseif (preg_match('/.+? - \[(.*)\]\(\/(.+?)\)$/', $line, $documents)) {
                    $heading = $heading;
                    $document = $documents[1];
                    $uri = str_replace('{{version}}', $branch, $documents[2]);
                }

                if ( ! isset($documentation[$heading])) {
                    $documentation[$heading] = [];
                }

                if ( ! is_null($document) && ! is_null($uri)) {
                    if ( ! isset($documentation[$heading][$document])) {
                        $documentation[$heading][$document] = $uri;
                    }
                }
            }

            $allDocumentation[$branch] = $documentation;
        }

        return $allDocumentation;
    }

    /**
     * @return $this
     * @throws \Illuminate\Contracts\Filesystem\FileNotFoundException
     */
    public function onNavigationItemSelected()
    {
        $onNavigationItemSelected = '';
        $markdownDirectory = dirname(__FILE__);

        foreach ($this->allDocumentation() as $branch => $documentation) {
            foreach ($documentation as $heading => $documents) {
                foreach ($documents as $document => $uri) {
                    $version = 'nav_' . str_replace('.', '_', $branch);
                    $android_id = $version . '_' . snake_case($heading . $document);
                    $android_id = str_replace(['&', '/'], '', $android_id);

                    if ($document === 'API Documentation') {
                        $onNavigationItemSelected .= <<<print
case R.id.{$android_id}:
    getSupportActionBar().setTitle("{$document}");
    getSupportActionBar().setSubtitle("{$heading}");
    mWebView.loadUrl("file:///android_asset/api/{$branch}/index.html");
    break;

print;
                    } else {
                        $onNavigationItemSelected .= <<<print
case R.id.{$android_id}:
    getSupportActionBar().setTitle("{$document}");
    getSupportActionBar().setSubtitle("{$heading}");
    onDocumentationItemSelected("{$uri}.html");
    break;

print;
                    }
                }
            }
        }

        $START = '// `onNavigationItemSelected()` - `START`';
        $END = '// `onNavigationItemSelected()` - `END`';
        $pathToFile = $markdownDirectory . '/../public/onNavigationItemSelected.txt';
        $this->files->put($pathToFile, $START . PHP_EOL .
            $onNavigationItemSelected . PHP_EOL .
            $END . PHP_EOL);

        return $this;
    }

    /**
     * @return $this
     * @throws \Illuminate\Contracts\Filesystem\FileNotFoundException
     */
    public function onDocumentationItemSelected()
    {
        $onDocumentationItemSelected = '';
        $markdownDirectory = dirname(__FILE__);

        foreach ($this->allDocumentation() as $branch => $documentation) {
            $duplicate = [];
            $documentation = array_map(function ($documents) use (&$duplicate) {
                foreach ($documents as $document => $uri) {
                    if (in_array($uri, $duplicate)) {
                        unset($documents[$document]);
                    } else {
                        $duplicate[] = $uri;
                    }
                }

                return $documents;
            }, $documentation);

            foreach ($documentation as $heading => $documents) {
                foreach ($documents as $document => $uri) {
                    $version = 'nav_' . str_replace('.', '_', $branch);
                    $android_id = $version . '_' . snake_case($heading . $document);
                    $android_id = str_replace(['&', '/'], '', $android_id);

                    $onDocumentationItemSelected .= <<<print
case "{$uri}.html":
    getSupportActionBar().setTitle("{$document}");
    getSupportActionBar().setSubtitle("{$heading}");
    mNavigationView.setCheckedItem(R.id.{$android_id});
    break;

print;
                }
            }
        }

        $START = '// `onDocumentationItemSelected()` - `START`';
        $END = '// `onDocumentationItemSelected()` - `END`';
        $pathToFile = $markdownDirectory . '/../public/onDocumentationItemSelected.txt';
        $this->files->put($pathToFile, $START . PHP_EOL .
            $onDocumentationItemSelected . PHP_EOL .
            $END . PHP_EOL);

        return $this;
    }

    /**
     * @throws \Illuminate\Contracts\Filesystem\FileNotFoundException
     */
    public function onActivityDrawer()
    {
        $markdownDirectory = dirname(__FILE__);
        $allDocumentation = $this->allDocumentation();

        foreach ($allDocumentation as $branch => $documentation) {
            $xmlContents = <<<print
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">

print;

            foreach ($documentation as $heading => $documents) {
                $android_heading = str_replace('&', '&amp;', $heading);

                $xmlContents .= <<<print
    <item android:title="{$android_heading}">
        <menu>

print;

                foreach ($documents as $document => $uri) {
                    $version = 'nav_' . str_replace('.', '_', $branch);
                    $android_id = $version . '_' . snake_case($heading . $document);
                    $android_id = str_replace(['&', '/'], '', $android_id);
                    $android_title = str_replace('&', '&amp;', $document);

                    $xmlContents .= <<<print
            <item
                android:id="@+id/{$android_id}"
                android:title="{$android_title}" />

print;
                }

                $xmlContents .= <<<print
        </menu>
    </item>

print;
            }

            $xmlContents .= <<<print
</menu>
print;

            $version = str_replace('.', '_', $branch);
            $pathToFile = $markdownDirectory . "/../public/menu/activity_{$version}_drawer.xml";
            $this->files->put($pathToFile, $xmlContents);
        }
    }
}

(new Handler())->handle();
