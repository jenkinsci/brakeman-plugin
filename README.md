A [Jenkins](http://jenkins-ci.org) plugin to collect the results of running [Brakeman](https://github.com/presidentbeef/brakeman) against Ruby on Rails applications.

## Requirements

This plugin only collects results of running Brakeman, it does not run Brakeman itself. 

## Installing the Plugin

### Through Jenkins

Navigate to `Manage Jenkins -> Manage Plugins -> Available` and look for "Brakeman". Click the checkbox and then the "Install" button at the bottom of the page.

### Manually

Download `brakeman.hpi` from [here](https://github.com/jenkinsci/brakeman-plugin/raw/master/brakeman.hpi).

Inside Jenkins, go to `Manage Jenkins -> Manage Plugins -> Advanced -> Upload Plugin`.

Upload `brakeman.hpi`.

## Usage

### Add Shell Command

How you run Brakeman really depends on how Jenkins and your application are configured.

Add/append a shell command to your job configuration like (this is just an example):

    gem install brakeman --no-doc &&
    brakeman --no-progress -no-exit-on-warn -o brakeman-output.json

Or, if [rvm](https://rvm.beginrescueend.com/) is available, you can use something like:

    bash -l -c 'rvm install 2.6.3 &&
    rvm use 2.6.3 &&
    rvm gemset create brakeman &&
    rvm gemset use brakeman &&
    gem install brakeman --no-rdoc --no-ri &&
    brakeman --no-progress -no-exit-on-warn -o brakeman-output.json'

Or, if you have Brakeman in your Gemfile:

    bundle exec brakeman --no-progress -no-exit-on-warn -o brakeman-output.json

### Publish Warnings

In the job configuration:

* Click "Add post-build action"
* Select "Record compiler warnings and static analysis results"
* Select "Brakeman" as the tool
* (Recommended) Uncheck "Ignore Failed Builds"
* (Optional) Check "Ignore Quality Gate" to only report new warnings once

## Development

### Testing without Existing Jenkins Server

Requires Maven 3. You will likely need to modify your environment as documented [here](https://wiki.jenkins-ci.org/display/JENKINS/Plugin+tutorial#Plugintutorial-SettingUpEnvironment).

After cloning the source, run this in the main directory:

    mvn hpi:run

This starts up a copy of Jenkins with the Brakeman plugin installed. This is not necessary if you just want to install the plugin.

### Building a New Plugin Package

This is to generate a new `brakeman.hpi`.

Requires Maven 3. You will likely need to modify your environment as documented [here](https://wiki.jenkins-ci.org/display/JENKINS/Plugin+tutorial#Plugintutorial-SettingUpEnvironment).

After cloning the source, run this in the main directory:

    mvn install

This builds a copy of the plugin in `./target/brakeman.hpi`
