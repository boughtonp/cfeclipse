CFEclipse Project
=================

The goal of the CFEclipse project is to create a plugin for the Eclipse
platform that provides a professional quality IDE for CFML developers.
CFEclipse offers all of the features found in traditional CFML editors
and several that are unique to CFEclipse.

As well as allowing developers to take advantage of the wealth of other
Eclipse plugins, CFEclipse comes with a developer friendly price tag...
It's FREE!

CFEclipse is an open-source project supported solely by volunteers!

See http://www.cfeclipse.org for information


## Java Projects included in this repository

* org.cfeclipse.cfeclipsecall
* org.cfeclipse.cfeclipsecall.plugin
* org.cfeclipse.cfml **Main CFEclipse Plugin**
* org.cfeclipse.cfml.cfunit
* org.cfeclipse.cfml.docsharesupport34
* org.cfeclipse.cfml.docsharesupport34.feature
* org.cfeclipse.cfml.docsharesupport35
* org.cfeclipse.cfml.docsharesupport35.feature
* org.cfeclipse.cfml.feature
* org.cfeclipse.cfml.frameworks
* org.cfeclipse.cfml.frameworks.fusebox
* org.cfeclipse.cfml.snippets
* org.cfeclipse.cfml.snippets.feature
* org.cfeclipse.cfml.snippets.update
* org.cfeclipse.cfml.templates.fragment
* org.cfeclipse.cfml.update
* org.cfeclipse.cfml.update-dev
* org.cfeclipse.cfml.update-nightly
* org.cfeclipse.snipex.server



## Building CFEclipse

### Step 1 - get the code
Clone repository to your local machine. (If you intend to send pull requests, 
you will most likely want to fork the repo on GitHub first.)

### Step 2 - get an Eclipse package with the plugin SDK
Ensure you have a suitable Eclipse setup - i.e. one that can compile Java code 
and includes the Eclipse "Plug-in Development Environment".

If you're unsure what this means, [Kepler Standard](http://www.eclipse.org/downloads/packages/eclipse-standard-431/keplersr1)
works fine, and can be setup alongside any existing Eclipse installations 
without interfering with them.

### Step 3 - setup the projects
Import these projects into Eclipse, by going to `File` > `Import` > `General` >
`Existing Projects Into Workspace`, entering the path to your code in the field
"select root directory", and ensuring "search for nested projects" is ticked.

* org.cfeclipse.cfml
* org.cfeclipse.cfml.cfunit
* org.cfeclipse.cfml.feature
* org.cfeclipse.cfml.frameworks
* org.cfeclipse.cfml.frameworks.fusebox
* org.cfeclipse.cfml.snippets
* org.cfeclipse.cfml.snippets.feature
* org.cfeclipse.cfml.snippets.update
* org.cfeclipse.cfml.update
* org.cfeclipse.cfml.update-dev
* org.cfeclipse.cfml.update-nightly
* org.cfeclipse.snipex.server

i.e. everything that is _NOT_ cfeclipsecall, docshare or templates.fragment

### Step 4 - open plugin.xml
Open `plugin.xml` from the `org.cfeclipse.cfml` project.

By default this will use the "Plug-in Manifest Editor", a wizard GUI for the
XML file - at this stage you do not need to worry about or change anything on
the screen this brings up.

### Step 5 - compile and run the code
Click the link "Launch an Eclipse Application" within the Testing section
in the right-hand column.

This will launch a new instance of Eclipse with the CFEclipse plugin, built
from the source code of the project.

### Problems Building?

If any of these steps did not work in some way, you can either hop onto the
IRC channel or post to the mailing list - details for both of these are in the 
Contact section below.


## Contributing

The CFEclipse project is entirely run by volunteers who donate their time to
working on CFEclipse; additional help is always very much welcomed!

Please feel free to introduce yourself on either IRC or the mailing list, and
let us know what sort of things you're interested in helping with.

NOTE: The git repository uses the nvie "git flow" structure - this means
**all pull requests should be branched from and issued to the develop branch**.


## Contact & Support

There are two CFEclipse mailing lists: cfeclipse-users is the general purpose
list for anyone using CFEclipse, while cfeclipse-dev is for helping to improve 
the software - irrespective of whether that is via code, docs, or whatever.

  CFEclipse Mailing List: http://groups.google.com/group/cfeclipse-users

  CFEclipse Development List: http://groups.google.com/group/cfeclipse-dev

You can also join the IRC channel on Freenode to chat in real-time:

  IRC Clients: irc://irc.freenode.net/#cfeclipse
	
  Web Client: https://webchat.freenode.net/?channels=%23cfeclipse

Finally, if you have found a bug with CFEclipse, please go ahead and provide 
details on the issue tracker at GitHub:

  Issue Tracker: https://github.com/cfeclipse/cfeclipse/issues

