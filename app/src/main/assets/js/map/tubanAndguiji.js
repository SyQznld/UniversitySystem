
//轨迹
var track =null;
//起点
var start_icon=null;
var end_icon=null;
//轨迹点  定时添加 每次添加两个点
var timerPolylineArr = [];
var timer = 0;
var timer1 = null;
var coordinateArray=[];
var folwLine_layerList = [];
//流程 路线绘制   定时显示动态绘制
function drawListTrack(arrayList,name){
console.log("------drawListTrack-- road_gps----" + arrayList);
       //清除上次点击显示的道路轨迹
        clearRoadTrack();

       var array=arrayList.split(",");
       for(var i=0;i<array.length;i++){
            var coordinate=Trim(array[i]);
            var list=coordinate.split(" ");
            var gps_lat=list[1];
            var gps_lng=list[0];

           var map_xy = proj4(mapCoordinateSystem,[gps_lng, gps_lat]);
           var map_xy_json = JSON.stringify(map_xy);
           var xy = map_xy_json.substring(1,map_xy_json.length - 1);
           var split = xy.split(",");
           var x = split[1];
           var y = split[0];

           coordinateArray.push([x,y]);
       }

        start_icon=AIMap.marker(coordinateArray[0], {icon: AIMap.icon({iconUrl: 'images/icon_point_s.png', iconSize: [70, 70]})}).addTo(map);

        timer1 = setInterval(function () { drawTimerLine(name) }, 500);
}




function drawTimerLine(name){
        var first = coordinateArray[timer];
        var second = coordinateArray[timer + 1];
        timerPolylineArr.push(first);
        timerPolylineArr.push(second);
        track = new AIMap.Polyline(timerPolylineArr,
        {
         "paused": false,   　　//暂停  初始化状态
         "reverse": false,　　//方向反转
         "delay": 3000,　　　　//延迟，数值越大效果越缓慢
         "dashArray": [5,10, 15,20],　//间隔样式
         "weight": 18,　　　　//线宽
         "opacity": 0.5,　　//透明度
         "color": "red",　//颜色
         "pulseColor": "#FFFFFF"　　//块颜色
        }).addTo(map);
        folwLine_layerList.push(track);
        map.fitBounds(track.getBounds());
        map.addLayer(track);
        timer = timer + 1;

        if(timer > coordinateArray.length - 2){
         end_icon=AIMap.marker(coordinateArray[coordinateArray.length-1], {icon: AIMap.icon({iconUrl: 'images/icon_point_e.png', iconSize: [70, 70]})}).addTo(map);
         track.bindPopup(" <div style='padding:20px;width:100%;'><span style='font-size: 2.0rem ;'> 路名：" + name  + "<br /> </span> ").openPopup();

         window.clearInterval(timer1);
         coordinateArray =[];
         timerPolylineArr =[];
         timer = 0;
        }
}





//清除流程轨迹信息
function clearRoadTrack(){
      if (folwLine_layerList.length > 0) {
            for (var i = 0; i < folwLine_layerList.length; i++) {
                if (folwLine_layerList[i] && map.hasLayer(folwLine_layerList[i])) {
                    map.removeLayer(folwLine_layerList[i]);
                }
            }
            folwLine_layerList = [];
        }
        if (start_icon && map.hasLayer(start_icon)) {
         map.removeLayer(start_icon);
        }
        if (end_icon && map.hasLayer(end_icon)) {
         map.removeLayer(end_icon);
        }
}


function Trim(str){
        return str.replace(/(^\s*)|(\s*$)/g, "");
       }







