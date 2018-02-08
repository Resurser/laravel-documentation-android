#!/bin/bash
base=$(pwd)
docs=${base}/docs

cd $docs
composer install

# Cleanup Before
rm -rf ${docs}/laravel

# Run Documentation
git clone https://github.com/laravel/docs.git ${docs}/laravel/master

declare -a branches=(
"4.0"
"4.1"
"4.2"
"5.0"
"5.1"
"5.2"
"5.3"
"5.4"
"5.5"
"5.6"
)

# Run Copy Directories
for branch in ${branches[@]}; do
	mkdir "${docs}/laravel/${branch}"
	cp -rf ${docs}/laravel/master/. "${docs}/laravel/${branch}"
done

# Run Switching Branches
for branch in ${branches[@]}; do
	cd "${docs}/laravel/${branch}"
	git checkout ${branch}
	git pull origin "${branch}"
done

php=$(which php)
${php} ${docs}/Makefile.php

# Cleanup After
rm -rf ${docs}/laravel
