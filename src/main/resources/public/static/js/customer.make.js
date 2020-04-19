$(function () {
    loadData01();
    loadData02();
    loadData03();
    loadData04();
})

function loadData01() {

    $.ajax({
        type:"post",
        url:ctx+"/customer/countCustomerMake",
        dataType:"json",
        success:function (data) {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: 'CRM 客户构成分析'
                },
                tooltip: {},
                legend: {
                    data:['数量']
                },
                xAxis: {
                    data:data.data1
                },
                yAxis: {},
                series: [{
                    name: '数量',
                    type: 'bar',
                    data:data.data2
                }]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }
    })


}


function loadData02() {

    $.ajax({
        type:"post",
        url:ctx+"/customer/countCustomerMake",
        dataType:"json",
        success:function (data) {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main02'));

            // 指定图表的配置项和数据
            var option ={
                xAxis: {
                    type: 'category',
                    data: data.data1
                },
                yAxis: {
                    type: 'value'
                },
                series: [{
                    data: data.data2,
                    type: 'line'
                }]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }
    })


}

function loadData03() {

    $.ajax({
        type:"post",
        url:ctx+"/customer/countCustomerMake02",
        dataType:"json",
        success:function (data) {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main03'));

           var option = {
                title: {
                    text: '某站点用户访问来源',
                    subtext: '纯属虚构',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c} ({d}%)'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left',
                    data: data.data1
                },
                series: [
                    {
                        name: '访问来源',
                        type: 'pie',
                        radius: '55%',
                        center: ['50%', '60%'],
                        data: data.data2,
                        emphasis: {
                            itemStyle: {
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
    })


}

function loadData04(){
    $.ajax({
        type:"post",
        url:ctx+"/customer/countCustomerMake02",
        dataType:"json",
        success:function (data) {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main04'));

            // 指定图表的配置项和数据
            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '南丁格尔玫瑰图',
                    subtext: '纯属虚构',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c} ({d}%)'
                },
                legend: {
                    left: 'center',
                    top: 'bottom',
                    data:  data.data1
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        magicType: {
                            show: true,
                            type: ['pie', 'funnel']
                        },
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                series: [
                    {
                        name: '半径模式',
                        type: 'pie',
                        radius: [20, 110],
                        center: ['25%', '50%'],
                        roseType: 'radius',
                        label: {
                            show: false
                        },
                        emphasis: {
                            label: {
                                show: true
                            }
                        },
                        data: data.data2
                    },
                    {
                        name: '面积模式',
                        type: 'pie',
                        radius: [30, 110],
                        center: ['75%', '50%'],
                        roseType: 'area',
                        data: data.data2
                    }
                ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }
    });
}
