/*angular.module('githubCrawler').factory('GraphService',
    ['$http',
     function(http) {
     console.log('graph service!');
          var shinyNewServiceInstance;
          // factory function body that constructs shinyNewServiceInstance
          return {
            loadGraph : function($http, url) {
                $http.get(url).success(
                        function(data) {
                            console.log(data);
                            var graph = new Graph(data);
                            var edges = graph.toEdges(data);
                            var nodes = graph.toNodes(data);
                            console.log('edges:' + edges);
                            console.log('nodes:' + nodes);
                            new Grapher().draw(nodes, edges);
                        });
            }
  };
}]);

function Graph(data) {
    var map = data;
    var Node = function(name) {
        return {
            data : {
                id : "" + name,
                name : name
            }
        };
    };
    var Edge = function(src, tgt) {
        return {
            data : {
                id : "" + src + tgt,
                weight : 10,
                source : src,
                target : tgt
            }
        };
    };
    this.toEdges = function() {
        var output = [];
        var edges = [];
        for (currName in map) {
            for (var i = 0; i < map[currName].length; i++) {
                var src = currName;
                var tgt = map[currName][i];
                if (edges.indexOf(tgt + src) === -1) {
                    edges.push(src + tgt);
                    output.push(new Edge(src, tgt));
                }
            }
        }
        return output;
    };
    this.toNodes = function() {
        var nodes = [];
        for (currName in map) {
            nodes.push(new Node(currName));
        }
        return nodes;
    };
}
*/