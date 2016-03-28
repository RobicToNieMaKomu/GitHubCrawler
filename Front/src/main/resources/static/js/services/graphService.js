angular.module('githubCrawler').service('graphService',
     function() {
        console.log('graph service!');
        this.drawGraph = function(data) {
            console.log(data);
            var graph = new Graph(data);
            var edges = graph.toEdges(data);
            var nodes = graph.toNodes(data);
            console.log('edges:' + edges);
            console.log('nodes:' + nodes);
            new Grapher().draw(nodes, edges);
        };
     }
);

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

function Grapher() {
    this.showLoadingTxt = function() {
        $('#cy').append("<strong>Loading data...</strong>");
    };
    this.hideLoadingTxt = function() {
        $('#cy').html('');
    };
    this.draw = function(n, e) {
    $('#cy').css("text-align","left");
    $('#cy').css("width",$('.row').width());
    $('#cy').css("height","300px");
    $('#cy').css("border","1px solid");

    var cy = cytoscape({
      container: $('#cy')[0],

      boxSelectionEnabled: false,
      autounselectify: true,

      style: cytoscape.stylesheet()
        .selector('node')
          .css({
            'content': 'data(name)',
            'text-valign': 'center',
            'color': 'white',
            'text-outline-width': 2,
            'text-outline-color': '#888'
          })
        .selector(':selected')
          .css({
            'background-color': 'black',
            'line-color': 'black',
            'target-arrow-color': 'black',
            'source-arrow-color': 'black',
            'text-outline-color': 'black'
          })
        .selector('.faded')
          .css({
            'opacity': 0.25,
            'text-opacity': 0
         }),

      elements: {
        nodes: n,
        edges: e
      },

      layout: {
        name: 'breadthfirst',
        concentric: function() {
            return this.data('weight');
        },
        levelWidth: function(nodes) {
            return 10;
        },
        padding: 10
      }
    });
    $("#cy").show();
}
}