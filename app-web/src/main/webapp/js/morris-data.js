$(function() {

    Morris.Area({
        element: 'morris-area-chart',
        data: [{
            período: '2010 Q1',
            Primário: 2666,
            Secundário: null,
            Superior: 2647
        }, {
            período: '2010 Q2',
            Primário: 2778,
            Secundário: 2294,
            Superior: 2441
        }, {
            período: '2010 Q3',
            Primário: 4912,
            Secundário: 1969,
            Superior: 2501
        }, {
            período: '2010 Q4',
            Primário: 3767,
            Secundário: 3597,
            Superior: 5689
        }, {
            período: '2011 Q1',
            Primário: 6810,
            Secundário: 1914,
            Superior: 2293
        }, {
            período: '2011 Q2',
            Primário: 5670,
            Secundário: 4293,
            Superior: 1881
        }, {
            período: '2011 Q3',
            Primário: 4820,
            Secundário: 3795,
            Superior: 1588
        }, {
            período: '2011 Q4',
            Primário: 15073,
            Secundário: 5967,
            Superior: 5175
        }, {
            período: '2012 Q1',
            Primário: 10687,
            Secundário: 4460,
            Superior: 2028
        }, {
            período: '2012 Q2',
            Primário: 8432,
            Secundário: 5713,
            Superior: 1791
        }],
        xkey: 'período',
        ykeys: ['Primário', 'Secundário', 'Superior'],
        labels: ['Primário', 'Secundário', 'Superior'],
        pointSize: 2,
        hideHover: 'auto',
        resize: true
    });

    Morris.Donut({
        element: 'morris-donut-chart',
        data: [{
            label: "Vapt vupt",
            value: 12
        }, {
            label: "Detran",
            value: 30
        }, {
            label: "Hugo",
            value: 20
        }],
        resize: true
    });

    Morris.Bar({
        element: 'morris-bar-chart',
        data: [{
            y: '2006',
            a: 100,
            b: 90
        }, {
            y: '2007',
            a: 75,
            b: 65
        }, {
            y: '2008',
            a: 50,
            b: 40
        }, {
            y: '2009',
            a: 75,
            b: 65
        }, {
            y: '2010',
            a: 50,
            b: 40
        }, {
            y: '2011',
            a: 75,
            b: 65
        }, {
            y: '2012',
            a: 100,
            b: 90
        }],
        xkey: 'y',
        ykeys: ['a', 'b'],
        labels: ['Série A', 'Série B'],
        hideHover: 'auto',
        resize: true
    });

});
