function addLoadEvent(func){
	var oldonload = window.onload;
	if (typeof window.onload != 'function') {
		window.onload = func;
	}else{
		window.onload=function(){
			oldonload();
			func();
		}
	};
}

function  initcharts(json){
	// 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
        // 指定图表的配置项和数据
        var option = {
        		 title: { text: 'ECharts' },
        		 tooltip: {},
        		 xAxis: {
        		   data: json.series
        		 },
        		 yAxis: {},
        		 series: [{
        		   name: '销量',
        		   type: 'bar',
        		   data: json.xAxis
        		  }]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
}

function  initcharts1(){
    // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main-1'));

        // 指定图表的配置项和数据
        var option = {

        backgroundColor: '#2c343c',

        title: {
            text: '饼图 ',
            left: 'center',
            top: 20,
            textStyle: {
                color: '#ccc'
            }
        },

        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },

        visualMap: {
            show: false,
            min: 80,
            max: 600,
            inRange: {
                colorLightness: [0, 1]
            }
        },
        series : [
            {
                name:'访问来源',
                type:'pie',
                radius : '55%',
                center: ['50%', '50%'],
                data:[
                    {value:335, name:'直接访问'},
                    {value:310, name:'邮件营销'},
                    {value:274, name:'联盟广告'},
                    {value:235, name:'视频广告'},
                    {value:400, name:'搜索引擎'}
                ].sort(function (a, b) { return a.value - b.value}),
                roseType: 'angle',
                label: {
                    normal: {
                        textStyle: {
                            color: 'rgba(255, 255, 255, 0.3)'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        lineStyle: {
                            color: 'rgba(255, 255, 255, 0.3)'
                        },
                        smooth: 0.2,
                        length: 10,
                        length2: 20
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#c23531',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
}

function  initcharts2(){
    // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main-2'));

        // 指定图表的配置项和数据
        var option = {
           
                title : {
                    text: '某站点用户访问来源',
                    subtext: '纯属虚构',
                    x:'center'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
                },
                series : [
                    {
                        name: '访问来源',
                        type: 'pie',
                        radius : '55%',
                        center: ['50%', '60%'],
                        data:[
                            {value:335, name:'直接访问'},
                            {value:310, name:'邮件营销'},
                            {value:234, name:'联盟广告'},
                            {value:135, name:'视频广告'},
                            {value:1548, name:'搜索引擎'}
                        ],
                        itemStyle: {
                            emphasis: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
}

function  initcharts3(){
     var myChart = echarts.init(document.getElementById('main-3'));
     option = {
    title: {
        text: '世界人口总量',
        subtext: '数据来自网络'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        }
    },
    legend: {
        data: ['2011年', '2012年' ,'2013年']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: {
       type: 'category',
       data: ['巴西','印尼','美国','印度','中国','世界人口(万)']
    },
    yAxis: {
        type: 'value',
        boundaryGap: [0, 0.01]
        
    },
    series: [
        {
            name: '2011年',
            type: 'bar',
            data: [18203, 23489, 29034, 104970, 131744, 630230]
        },
        {
            name: '2012年',
            type: 'bar',
            data: [19325, 23438, 31000, 121594, 134141, 681807]
        },
        {
            name: '2013年',
            type: 'bar',
            data: [19525, 23538, 31500, 122094, 138181, 691807]
        }
    ]
};
}

function  charts(){
	doGet("http://localhost:8080/chart/creat/2",function(result){
		var json = JSON.parse(result);
		alert(json.series);
		initcharts(json);
	});
}




addLoadEvent(charts);
