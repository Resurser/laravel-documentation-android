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
    protected $directory = __DIR__ . '\\docs';

    /**
     * @var float
     */
    protected $version = 5.3;

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
        $this->git()
            ->pull();

        $ruler = $this->ruler();

        /** @var SplFileInfo $fileInfo */
        foreach ($this->files->allFiles($this->directory) as $fileInfo) {
            $getContents = $this->files->get($fileInfo->getRealPath());
            $getContents = $this->replaceLinks($this->version, $this->markdown($getContents));

            if (preg_match_all('/href="(.+?)"/', $getContents, $matches)) {
                for ($i = 0; $i < count($matches[0]); $i++) {
                    $value = $matches[1][$i];
                    if ( ! starts_with($value, $ruler)) {
                        $relative = $this->replacer($value);
                        $getContents = str_replace('href="' . $value . '"', 'href="' . $relative . '"', $getContents);
                    }
                }
            }

            $getBasename = $fileInfo->getBasename('.md');
            $this->files->put(__DIR__ . '/app/src/main/assets/' . $getBasename . '.html', $getContents);
        }
    }

    /**
     * @return array
     */
    protected function ruler()
    {
        return [
            'file:///android_asset/',
            '//',
            'http://',
            'https://',
            '_',
            '#',
        ];
    }

    /**
     * @return $this
     */
    protected function git()
    {
        if ( ! $this->files->isDirectory(__DIR__ . '/docs')) {
            exec('git clone https://github.com/laravel/docs.git ' . __DIR__ . '/docs', $output, $returnCode);
        }

        return $this;
    }

    /**
     * @return $this
     */
    protected function pull()
    {
        exec('cd ' . __DIR__ . '/lumen-docs && git pull');

        return $this;
    }

    /**
     * @param $value
     * @param SplFileInfo $fileInfo
     * @return string
     */
    protected function replacer($value)
    {
        $android_asset = $value;
        if (preg_match('/(.*)#/', $value, $matches)) {
            $android_asset = $matches[1];
        }

        if (starts_with($android_asset, "/docs/{$this->version}/")) {
            $specified = $android_asset;
            if ( ! pathinfo($specified, PATHINFO_EXTENSION)) {
                $specified .= '.html';
            }

            $absolute = str_replace_first("/docs/{$this->version}/", 'file:///android_asset/', $specified);
            $relative = str_replace(DIRECTORY_SEPARATOR, '/', $absolute);

            return $relative;
        }

        return $value;
    }

    /**
     * @param string $version
     * @param string $getContents
     * @return string
     */
    protected function replaceLinks($version, $getContents)
    {
        return str_replace("{{version}}", $version, $getContents);
    }

    /**
     * @param string $getContents
     * @return string
     */
    protected function markdown($getContents)
    {
        return (new ParsedownExtra)->text($getContents);
    }
}

(new Handler)->handle();
