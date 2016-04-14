angular.module('githubCrawler').service('graphService',
     [
        'csLayoutFactory',
        function(csLayoutFactory) {
            var that = this;
            var draw = function(data, type) {
                var graph = new Graph(data);
                var edges = graph.toEdges(data);
                var nodes = graph.toNodes(data);
                new Grapher().draw(nodes, edges, type);
            };
            this.recentGraph = {};
            this.drawGraph = function(data) {
                that.recentGraph = data;
                draw(data, csLayoutFactory.getDefault());
            };
            this.redraw = function(type) {
               draw(that.recentGraph, type);
            };
     }]
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
    this.draw = function(n, e, l) {
        $('#cy').css("text-align","left");
        $('#cy').css("width",$('.row').width());
        var h = 2 * $('.site-wrapper').height()/5;// - $('.cover-heading.ng-binding').height() - $('.lead.ng-binding').height() - $('.inner').height();
        $('#cy').css("height", "" + h + "px");
        $('#cy').css("border","1px solid");
        $('#cy').css("background", "#265B6A");

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
                'line-color': 'black'
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
          layout: l
        });
        $("#cy").show();
        cy.center();
    }
}