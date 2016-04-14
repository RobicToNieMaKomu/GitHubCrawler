angular.module('githubCrawler').service('csLayoutFactory',
    function() {
        var ls = {
                'concentric' : {
                    name: 'concentric',
                    concentric: function( node ){
                      return node.degree();
                    },
                    levelWidth: function( nodes ){
                      return 2;
                    }
                },
                'breadthfirst' : {
                    name: 'breadthfirst',
                    concentric: function() {
                        return this.data('weight');
                    },
                    levelWidth: function(nodes) {
                        return 10;
                    },
                    padding: 2
                },
                "grid" : {
                     name: 'grid',
                     padding: 10
                }
        };
        this.getDefault = function() {
            return ls['Grid'];
        };
        this.allLayouts = function() {
            return ls;
        };
    });