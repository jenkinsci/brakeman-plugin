A Hudson/[Jenkins](http://jenkins-ci.org) plugin to run [Brakeman](https://github.com/presidentbeef/brakeman) against Ruby on Rails applications and track the results.

## Requirements

This plugin requires the [Static Analysis Utilities Plugin](https://wiki.jenkins-ci.org/display/JENKINS/Static+Code+Analysis+Plug-ins) (which is also called "analysis-core") version >= 1.78. The plugin is available in Jenkins under `Manage Jenkins -> Manage Plugins -> Available -> Static Analysis Utilities`.

You will also need to have Ruby and RubyGems installed on the Jenkins/Hudson server in order to run Brakeman.

## Installing the Plugin

### Through Jenkins

Navigate to `Manage Jenkins -> Manage Plugins -> Available` and look for "Brakeman". Click the checkbox and then the "Install" button at the bottom of the page.

### Manually

Download `brakeman.hpi` from [here](https://github.com/jenkinsci/brakeman-plugin/raw/master/brakeman.hpi).

Inside Jenkins, go to `Manage Jenkins -> Manage Plugins -> Advanced -> Upload Plugin`.

Upload `brakeman.hpi`.

## Usage

### Add Shell Command

Add/append a shell command to your job configuration like (this is just an example):

    gem install brakeman --no-rdoc --no-ri &&
    brakeman -o brakeman-output.json

Or, if [rvm](https://rvm.beginrescueend.com/) is available, you can use something like:

    bash -l -c 'rvm use 2.4.1 &&
    rvm gemset create brakeman &&
    rvm gemset use brakeman &&
    gem install brakeman --no-rdoc --no-ri &&
    brakeman -o brakeman-output.json'

The version of Ruby may vary according to your setup.

### Publish Warnings

Click 'Publish Brakeman warnings' to enable the Brakeman.

Some adjustment may need to be done regarding paths. Brakeman needs to be run at the root of the application or supplied with the path. The output file specified on the commandline needs to be synched with the output file specified as a plugin option.

### Pipeline usage

Assuming build environment is configured as above:

    node {
      sh 'brakeman -o brakeman-output.json'
      publishBrakeman 'path/to/brakeman-output.json'
    }

## Testing without Existing Jenkins Server

Requires Maven2. You will likely need to modify your environment as documented [here](https://wiki.jenkins-ci.org/display/JENKINS/Plugin+tutorial#Plugintutorial-SettingUpEnvironment).

After cloning the source, run this in the main directory:

    mvn hpi:run

This starts up a copy of Jenkins with the Brakeman plugin installed. This is not necessary if you just want to install the plugin.

## Building a New Plugin Package

This is to generate a new `brakeman.hpi`.

Requires Maven 3. You will likely need to modify your environment as documented [here](https://wiki.jenkins-ci.org/display/JENKINS/Plugin+tutorial#Plugintutorial-SettingUpEnvironment).

After cloning the source, run this in the main directory:

    mvn install

This builds a copy of the plugin in `./target/brakeman.hpi`
