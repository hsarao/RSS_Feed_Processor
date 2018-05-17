# RSS_Feed_Processor

The RSS Feed Processor is an android studio which retrieves RSS feed from Winnipeg free press and talksport's football feeds and parse out and display those feeds to UI.  This is done in asynchronous manner by performing the SAX parsing of the RSS feed mark up without blocking the UI thread.

The app has two activites -(1) Showing a ListView of items in the RSS feed (display of ​only title and date fields), and (2) Showing the additional details of an item selected in activity number one. Activity two also allows the user to open a link to web content in the web browser in a WebView.

The app also has a couple of settings from where you can switch the feed sources between Winnipeg Free Press and TalkSport and refresh the feed.
