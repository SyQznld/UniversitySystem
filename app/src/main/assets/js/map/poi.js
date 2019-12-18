
var poiMarker = null;
var poiMarkerPop = null;
var poi_zjIcon=null;
var poi_layerList = [];
var poi_layerZJList = [];



//根据传入的坐标经纬度定位到地图上  poi点定位 单个
function loadSinglePoi(lng,lat,name,type){
    clearPoiMarker();  //清除上一个poimarker点

     var map_xy = proj4(mapCoordinateSystem,[lng, lat]);
     var map_xy_json = JSON.stringify(map_xy);
     var xy = map_xy_json.substring(1,map_xy_json.length - 1);
     var split = xy.split(",");
     var x = split[1];
     var y = split[0];
    var _point = AIMap.latLng(x, y);
    var poiIconUrl = null;
    switch(type){
       case "生活服务":
            poiIconUrl =  'images/poi_shenghuo.png';
            break;
       case "教学楼":
            poiIconUrl =  'images/poi_jiaoxuelou.png';
            break;
       case "消防点":
            poiIconUrl =  'images/poi_xiaofang.png';
            break;
       case "紧急报警点":
            poiIconUrl =  'images/poi_baojingdian.png';
            break;
       case "教学单位":
            poiIconUrl =  'images/poi_jiaoxue.png';
            break;
       case "餐饮":
            poiIconUrl =  'images/poi_canyin.png';
            break;
       case "出行":
            poiIconUrl =  'images/poi_park.png';
            break;
       case "全景":
            poiIconUrl =  'images/poi_vr.png';
            break;
       case "文体娱乐":
            poiIconUrl =  'images/poi_yule.png';
            break;
       case "住宿":
            poiIconUrl =  'images/poi_zhusu.png';
            break;
       case "学校大门":
            poiIconUrl =  'images/poi_gate.png';
            break;
       case "超市":
            poiIconUrl =  'images/poi_shenghuo.png';
            break;
       case "景观":
            poiIconUrl =  'images/poi_jingguan.png';
            break;
       case "教辅单位":
            poiIconUrl =  'images/poi_jiaofu.png';
            break;
       case "ATM":
            poiIconUrl =  'images/poi_ATM.png';
            break;
       case "医院":
            poiIconUrl =  'images/poi_hospital.png';
            break;
       default:
            poiIconUrl =  'images/poi_other.png';
            break;
    }
    poiMarker = AIMap.marker(_point, {
                                poiName:name,
                                geom:_point,
                                icon: AIMap.icon({
                                iconUrl: poiIconUrl,
                                iconSize: [60, 60]
                                })
                           });

     poiMarkerPop = AIMap.popup()
                    .setLatLng(_point)
                    .setContent(" <div style='padding:20px;'><span style='font-size: 2.0rem ;'> " + name +   " </span> </div>")
                    .addTo(map);

     poiMarker.on('click', function(e) {
     var name = e.target.options.poiName;
     var geom = e.target.options.geom;
     if(type == "全景"){
         android.showVR(name);
     }else{
          if(type == "出行"){
              poiMarkerPop = AIMap.popup()
                           .setLatLng(geom)
                           .setContent(" <div style='padding:20px;'><span style='font-size: 2.0rem ;'> " + name + " <br><span style='font-size: 1.0rem ;'> " + "剩余" + (Math.round(Math.random()*10) + 5) + "个车位" +  " </span> </div>")
                           .addTo(map);

          }else{
              poiMarkerPop = AIMap.popup()
                           .setLatLng(geom)
                           .setContent(" <div style='padding:20px;'><span style='font-size: 2.0rem ;'> " + name +   " </span> </div>")
                           .addTo(map);
          }
     }
     });
    map.addLayer(poiMarker);
    map.setView(new AIMap.latLng(x, y), map.getZoom());
//   poi_zjIcon = new AIMap.divIcon({ className: 'my-div-icon', html: "<div class='Icontitle' style='color:black; font-size:25px;font-weight: normal; width:350px;text-align:center auto;margin-top:-55px;margin-left:-35px;'>" + name+  " <br><span style='font-size: 1.0rem ;'> " + "剩余" + (Math.round(Math.random()*10) + 5) + "个车位" + "  </span></div>" });

}





//根据poi点传入的类型 显示或隐藏列表数据  加载某一种类型的
function loadPoiMarkersByType(poiString,type,isShow){
    //poiString     {"gid":1,"id":1,"name":"静馨公寓3","x":113.651719,"y":34.86691,"type":"住宿","geom":{"X":113.65171944962474,"Y":34.866909982347373,"SRID":0},"quanjingid":null,"quanjingimg":null,"campus":"东校区"},,,,,,,
     if(isShow){
      var data = eval("(" + poiString + ")");
         if (data != null && data.length > 0) {
         var typePoiMarker = null;
         var cur_function_markers = [];
         var feature_length = data.length;
         console.log("loadPoiMarkersByType=====" + feature_length);
         try{
            for (var i = 0; i < feature_length; i++) {
             if(data[i].geom!=null){
                 var poiName = data[i].name;
                 var zuobiaoArray = data[i].geom;
                 //转换坐标 84---》墨卡托
                  var map_xy = proj4(mapCoordinateSystem,[zuobiaoArray.X, zuobiaoArray.Y]);
                  var map_xy_json = JSON.stringify(map_xy);
                  var xy = map_xy_json.substring(1,map_xy_json.length - 1);
                  var split = xy.split(",");
                  var x = split[1];
                  var y = split[0];
                  var _point = AIMap.latLng(x, y);
       //分不同类型 加载显示不同marker点图标
       var poiIconUrl = null;
       var typeStr = data[i].type;
       var quanjingtype = data[i].quanjingtype;
       var quanjingname = data[i].name;
   switch(typeStr){
       case "生活服务":
            poiIconUrl =  'images/poi_shenghuo.png';
            break;
       case "教学楼":
            poiIconUrl =  'images/poi_jiaoxuelou.png';
            break;
       case "消防点":
            poiIconUrl =  'images/poi_xiaofang.png';
            break;
       case "紧急报警点":
            poiIconUrl =  'images/poi_baojingdian.png';
            break;
       case "教学单位":
            poiIconUrl =  'images/poi_jiaoxue.png';
            break;
       case "餐饮":
            poiIconUrl =  'images/poi_canyin.png';
            break;
       case "餐厅":
            poiIconUrl =  'images/poi_canyin.png';
            break;
       case "出行":
            poiIconUrl =  'images/poi_park.png';
            break;
       case "全景":
             if(quanjingtype == "校貌"){
                 poiIconUrl =  'images/poi_vr.png';
             }else if(quanjingtype == "教学楼"){
                 poiIconUrl =  'images/poi_jiaoxuelou.png';
              }else if(quanjingtype == "宿舍"){
                 poiIconUrl =  'images/poi_zhusu.png';
              }else if(quanjingtype == "食堂"){
                 poiIconUrl =  'images/poi_canyin.png';
              }

               if(quanjingname == "综合训练馆"){
                 poiIconUrl =  'images/poi_yule.png';
              }

//            poiIconUrl =  'images/poi_vr.png';
            break;
       case "文体娱乐":
            poiIconUrl =  'images/poi_yule.png';
            break;
       case "住宿":
            poiIconUrl =  'images/poi_zhusu.png';
            break;
       case "学校大门":
            poiIconUrl =  'images/poi_gate.png';
            break;
       case "超市":
            poiIconUrl =  'images/poi_shenghuo.png';
            break;
       case "景观":
            poiIconUrl =  'images/poi_jingguan.png';
            break;
       case "教辅单位":
            poiIconUrl =  'images/poi_jiaofu.png';
            break;
       case "ATM":
            poiIconUrl =  'images/poi_ATM.png';
            break;
       case "医院":
            poiIconUrl =  'images/poi_hospital.png';
            break;
       default:
            poiIconUrl =  'images/poi_other.png';
            break;
        }
                 typePoiMarker = AIMap.marker(_point, {
                                                       geom:_point,
                                                       name:poiName,
                                                       singlePoiType:typeStr,
                                                       icon: AIMap.icon({
                                                       iconUrl: poiIconUrl,
                                                       iconSize: [60, 60]
                                                       })
                                                   });

                 typePoiMarker.on('click', function(e) {
                 console.log("------marker点击事件------"+ e.target.options.singlePoiType + "     " + e.target.options.name);
                 var poiName = e.target.options.name;
                 var geom = e.target.options.geom;
                 var singlePoiType = e.target.options.singlePoiType;
                 if(singlePoiType == "全景"){
                    android.showVR(poiName);
                 }else{
                     if(singlePoiType == "出行"){
                         poiMarkerPop = AIMap.popup()
                                      .setLatLng(geom)
                                      .setContent(" <div style='padding:20px;'><span style='font-size: 2.0rem ;'> " + poiName + " <br><span style='font-size: 1.0rem ;'> " + "剩余" + (Math.round(Math.random()*10) + 5) + "个车位" +  " </span> </div>")
                                      .addTo(map);

                     }else{
                         poiMarkerPop = AIMap.popup()
                                      .setLatLng(geom)
                                      .setContent(" <div style='padding:20px;'><span style='font-size: 2.0rem ;'> " + poiName +   " </span> </div>")
                                      .addTo(map);
                     }
                 }
                 });
                 map.addLayer(typePoiMarker);

                var _layer = { layer: typePoiMarker,poiType:type};
                poi_layerList.push(_layer);
                map.setView(new AIMap.latLng(x, y), 17);
//                 map.setView([4145344.261902, 12651567.898733], 17);

             }
            }
         }catch(eeee){
         console.log("--------------显示某一种类型poi--" + eeee);
         }
         }
     }else{
         unloadPoiTypeMarkers(type);
     }
}




//取消某一个marker点
function clearPoiMarker() {
    if (poiMarker != null && map.hasLayer(poiMarker)) {
        map.removeLayer(poiMarker);
        poiMarker = null;
    }

    if (poiMarkerPop != null && map.hasLayer(poiMarkerPop)) {
        map.removeLayer(poiMarkerPop);
        poiMarkerPop = null;
    }

}



//poi点按照类型取消  取消某一种类型marker图层以及携带的注记
function unloadPoiTypeMarkers(poiType){
    for (var i = 0; i < poi_layerList.length; i++) {
        if (poi_layerList[i].poiType == poiType) {
            var _features = poi_layerList[i].layer;
            map.removeLayer(_features);
        }
    }
    for (var i = 0; i < poi_layerZJList.length; i++) {
        if (poi_layerZJList[i].poiZjType == poiType) {
            var _features = poi_layerZJList[i].layer;
            map.removeLayer(_features);
        }
    }

}









var dzjgMarker = null;
//根据传入的坐标经纬度定位到地图上   党政机关
function zoomToLatLngDZJG(gps_lat,gps_lng,name,tel,jianjie,address){
   //没有第二坐标系时，默认是WGS84坐标系。forward:转成定义的坐标系；inverse：由定义的坐标系转成WGS84
     var map_xy = proj4(mapCoordinateSystem).forward([gps_lng, gps_lat]);
     var map_xy_json = JSON.stringify(map_xy);
     var xy = map_xy_json.substring(1,map_xy_json.length - 1);
     var split = xy.split(",");
     var lat = split[1];
     var lng = split[0];

    //清除上一个marker点
    clearDZJGMarker();
    var _point = AIMap.latLng(lat, lng);
    dzjgMarker = AIMap.marker(_point, {
                                icon: AIMap.icon({
                                iconUrl: 'images/poi_jigou.png',
                                iconSize: [60, 60],
                                iconAnchor: [20, 30]
                                })
                            });
    map.addLayer(dzjgMarker);
    //绑定参数，显示弹框在 AIMAP上
//    dzjgMarker.bindPopup("<div  style='padding:20px;'><span class='dzjg_class' style='font-size: 2.0rem ;'> 名称：" + name + "<br />联系方式：" + tel + "<br/>简介：" + jianjie + "<br/>地址：" + address + "  </span><span class='btn'>[展开]</span></div>").openPopup();

    dzjgMarker.bindPopup(" <div style='padding:20px;'><span class='length' style='font-size: 2.0rem ;'> 名称：" + name + "<br />联系方式：" + tel + "<br/>简介：" + jianjie + "<br/>地址：" + address + "</span><span style='font-size: 2.0rem ; color:blue' class='bt'>[展开]</span></div>").openPopup();
   map.setView(new AIMap.latLng(lat, lng), map.getZoom());


   //党政机关显示过长 展开或收起功能
   var wordLength = function (a, b) {//a是文字的class，b是按钮的class
           var ininWordArr = [];//用来存储所有段落的文字
           var nowWordArr = [];//用来存储隐藏后所有段落的文字
           $(a).each(function () {
               var initWordLength = $(this).html().length;//当前段落文字的长度
               var ininWord = $(this).html();
               ininWordArr.push(ininWord);
               var nowWord;
               if (initWordLength > 100) {
                   nowWord = $(this).html().substr(0, 100) + '......';
               } else {
                   nowWord = $(this).html();
                   $(this).next().css('display', 'none');
               }
               $(this).html(nowWord);
               nowWordArr.push(nowWord);
           });
           $(document).on('click',b, function () {

               var i = $(b).index($(this));
               if ($(this).html() == '[展开]') {
                   $(this).html('[收起]');
                   $(this).prev().html(ininWordArr[i]);
               } else {
                   $(this).html('[展开]');
                   $(this).prev().html(nowWordArr[i]);
               }
               return false;
           })
       }('.length', '.bt')
}


function clearDZJGMarker() {
    if (dzjgMarker != null && map.hasLayer(dzjgMarker)) {
        map.removeLayer(dzjgMarker);
        dzjgMarker = null;
    }
}






var flowFloorMarker = null;
var flow_markerArray = new Array();
var flowPopArray = new Array();
//根据传入的坐标经纬度定位到地图上   流程关联楼栋
function zoomToFlowFloor(gps_lat,gps_lng,name,type){
   //没有第二坐标系时，默认是WGS84坐标系。forward:转成定义的坐标系；inverse：由定义的坐标系转成WGS84
     var map_xy = proj4(mapCoordinateSystem).forward([gps_lng, gps_lat]);
     var map_xy_json = JSON.stringify(map_xy);
     var xy = map_xy_json.substring(1,map_xy_json.length - 1);
     var split = xy.split(",");
     var lat = split[1];
     var lng = split[0];

    if(type == "single"){
     //清除上一个marker点
        clearFlowFloorMarker();
    }
    var _point = AIMap.latLng(lat, lng);
    flowFloorMarker = AIMap.marker(_point, {
                                icon: AIMap.icon({
                                iconUrl: 'images/poi_flow.png',
                                iconSize: [60, 60],
                                iconAnchor: [20, 30]
                                })
                            });

    map.addLayer(flowFloorMarker);
//    //绑定参数，显示弹框在 AIMAP上
//    flowFloorMarker.bindPopup(" <div style='padding:20px;width:100%;'><span style='font-size: 2.0rem ;'> " + name + " </span> </div>").openPopup();
    flowFloorMarker.bindPopup(" <div style='padding:20px;'><span style='font-size: 2.0rem ;'> " + name + " </span> </div>");

    var popup = AIMap.popup()
        .setLatLng(_point)
        .setContent(" <div style='padding:20px;'><span style='font-size: 2.0rem ;'> " + name + " </span> </div>")
//        .openOn(map);
        .addTo(map);


    flow_markerArray.push(flowFloorMarker);
    flowPopArray.push(popup);
    map.setView(new AIMap.latLng(lat, lng), map.getZoom());
}



function clearFlowFloorMarker() {
   for (var i = 0; i < flow_markerArray.length; i++) {
        map.removeLayer(flow_markerArray[i]);
        if (i == flow_markerArray.length - 1) {
            flow_markerArray = new Array();
        }
    }

      for (var i = 0; i < flowPopArray.length; i++) {
            map.removeLayer(flowPopArray[i]);
            if (i == flowPopArray.length - 1) {
                flowPopArray = new Array();
            }
        }
}









