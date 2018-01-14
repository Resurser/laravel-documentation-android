<?php

require_once __DIR__ . '/vendor/autoload.php';

class Handler
{
    /**
     * @var \Illuminate\Filesystem\Filesystem
     */
    protected $files;

    /**
     * @var string
     */
    protected $apiRepository = 'https://github.com/laravel/framework.git';

    /**
     * @var string
     */
    protected $apiDirectory = __DIR__ . '\\laravel';

    /**
     * @var string
     */
    protected $apiHtmlDirectory = __DIR__ . '\\app\\src\\main\\assets\\api';

    /**
     * @var string
     */
    protected $docsRepository = 'https://github.com/laravel/docs.git';

    /**
     * @var string
     */
    protected $docsMarkdownDirectory = __DIR__ . '\\docs';

    /**
     * @var string
     */
    protected $docsHtmlDirectory = __DIR__ . '\\app\\src\\main\\assets\\docs';

    /**
     * @var array
     */
    protected $docsBranches = [
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
        # 'master',
    ];

    /**
     * @var array
     */
    protected $docsSubjects = [
        'file:///android_asset/',
        '//',
        'http://',
        'https://',
        '_',
        '#',
    ];

    /**
     * @return void
     */
    public function __construct()
    {
        $this->files = new \Illuminate\Filesystem\Filesystem();
    }

    /**
     * @return void
     */
    public function handle()
    {
        # API
        # $this->build_api();

        # Documentation
        $this->build_docs();
    }

    /**
     * @return $this
     */
    protected function build_api()
    {
        $this->clone_framework()
            ->sami_generator();
    }

    /**
     * @return $this
     */
    protected function clone_framework()
    {
        $directory = $this->apiDirectory . '\\master';

        if ( ! $this->files->isDirectory($directory)) {
            $command = sprintf('git clone "%s" "%s"', $this->apiRepository, $directory);
        } else {
            $command = sprintf('cd "%s" && git pull', $directory);
        }

        exec($command);

        return $this;
    }

    /**
     * @return $this
     */
    protected function sami_generator()
    {
        ini_set('memory_limit', '-1');

        $command = sprintf('"%s" update "%s"', __DIR__ . '/vendor/bin/sami.php.bat', __DIR__ . '/sami.php');

        exec($command);

        $buildDirectory = $this->apiDirectory . '\\build';
        $this->files->copyDirectory($buildDirectory, $this->apiHtmlDirectory);

        return $this;
    }

    /**
     * @return $this
     */
    protected function build_docs()
    {
        $this->separate_branches()
            ->markdown_generator();

        return $this;
    }

    /**
     * @return $this
     */
    protected function separate_branches()
    {
        $docsRepository = $this->docsRepository;
        $masterDirectory = $this->docsMarkdownDirectory . '\\master';
        if ( ! $this->files->isDirectory($masterDirectory)) {
            $masterCommand = sprintf('git clone "%s" "%s"', $docsRepository, $masterDirectory);
        } else {
            $masterCommand = sprintf('cd "%s" && git pull', $masterDirectory);
        }

        exec($masterCommand);

        foreach ($this->docsBranches as $branch) {
            $branchDirectory = $this->docsMarkdownDirectory . '\\' . $branch;

            if ( ! $this->files->isDirectory($branchDirectory)) {
                $this->files->copyDirectory($masterDirectory, $branchDirectory);
                $branchCommand = sprintf('cd "%s" && git checkout "%s"', $branchDirectory, $branch);
            } else {
                $branchCommand = sprintf('cd "%s" && git pull', $branchDirectory);
            }

            exec($branchCommand);
        }

        return $this;
    }

    /**
     * @return $this
     */
    protected function markdown_generator()
    {
        foreach ($this->docsBranches as $branch) {
            $branchDirectory = $this->docsMarkdownDirectory . '\\' . $branch;
            $htmlBranchDirectory = $this->docsHtmlDirectory . '\\' . $branch;
            if ( ! $this->files->isDirectory($htmlBranchDirectory)) {
                $this->files->makeDirectory($htmlBranchDirectory);
            }

            /** @var SplFileInfo $fileInfo */
            foreach ($this->files->allFiles($branchDirectory) as $fileInfo) {
                try {
                    $getContents = $this->files->get($fileInfo->getRealPath());
                    $markdownContents = (new ParsedownExtra)->text($getContents);
                    $htmlContents = str_replace("{{version}}", $branch, $markdownContents);

                    if (preg_match_all('/href="(.+?)"/', $htmlContents, $hrefs)) {
                        for ($i = 0; $i < count($hrefs[0]); $i++) {
                            $href = $hrefs[1][$i];
                            if ( ! starts_with($href, $this->docsSubjects)) {
                                $relative = $this->android_asset($href, $branch);
                                $htmlContents = str_replace('href="' . $href . '"', 'href="' . $relative . '"', $htmlContents);
                            }
                        }
                    }

                    $getBasename = $fileInfo->getBasename('.md');
                    $pathToFile = $htmlBranchDirectory . '\\' . $getBasename . '.html';
                    $this->files->put($pathToFile, $htmlContents);
                } catch (Exception $ex) {
                    //
                }
            }
        }

        return $this;
    }

    /**
     * @param string $value
     * @param string $version
     * @return string
     */
    protected function android_asset($value, $version)
    {
        $android_asset = $value;
        if (preg_match('/(.*)#/', $value, $matches)) {
            $android_asset = $matches[1];
        }

        if (starts_with($android_asset, "/docs/{$version}/")) {
            $specified = $android_asset;
            if ( ! pathinfo($specified, PATHINFO_EXTENSION)) {
                $specified .= '.html';
            }

            $absolute = str_replace_first("/docs/{$version}/", 'file:///android_asset/', $specified);
            $relative = str_replace(DIRECTORY_SEPARATOR, '/', $absolute);

            return $relative;
        }

        return $value;
    }
}

(new Handler)->handle();
