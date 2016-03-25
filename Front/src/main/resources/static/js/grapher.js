function Grapher() {
    this.showLoadingTxt = function() {
        $('#cy').append("<strong>Loading data...</strong>");
    };
    this.hideLoadingTxt = function() {
        $('#cy').html('');
    };
    this.draw = function(n, e) {

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
          }),

      elements: {
        nodes: n,
        edges: e
      },

      layout: {
        name: 'grid',
        padding: 10
      }
    });
}
}