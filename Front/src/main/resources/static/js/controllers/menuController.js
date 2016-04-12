angular.module('githubCrawler').controller(
		'menuController', function() {
            this.tabs = [{name : 'Crawler', id : 'crawler'},
                        {name : 'Recent searches', id : 'recentSearches'},
                        {name : 'Full topology', id : 'fullTopology'}];

            var Panel = function(title, subtitle, type) {
                console.log('new panel:' + title);
                this.title = title;
                this.subtitle = subtitle;
                this.type = type;
            }
            var panels = {
                'crawler' : new Panel(
                'Find GitHub connections',
                'Enter username and choose how deeply GitHubCrawler should search for connections',
                'crawler',
                'showCrawler'
                ),
                'recentSearches' : new Panel(
                'Show recent searches',
                'Display recent GitHub connections searched by other users',
                'recentSearches',
                'showRecentSearches'
                 ),
                 'fullTopology' : new Panel(
                 'Show full topology',
                 'Constructs one graph by using all historic searches',
                 'fullTopology',
                 'showTopology'
                 )};
            this.panel = panels['crawler'];
            this.setPanel = function(what) {
                this.panel = panels[what];
            }
		}
);