﻿var lat = 0; var lng = 0;
var ll = [];
var llname = "";
var jsonData="";
var xx = 2;
var layerList = [];

//地图投影坐标系，从切片文件中获取
var mapCoordinateSystem = 'PROJCS["WGS_1984_Web_Mercator_Auxiliary_Sphere",GEOGCS["GCS_WGS_1984",DATUM["D_WGS_1984",SPHEROID["WGS_1984",6378137.0,298.257223563]],PRIMEM["Greenwich",0.0],UNIT["Degree",0.0174532925199433]],PROJECTION["Mercator_Auxiliary_Sphere"],PARAMETER["False_Easting",0.0],PARAMETER["False_Northing",0.0],PARAMETER["Central_Meridian",0.0],PARAMETER["Standard_Parallel_1",0.0],PARAMETER["Auxiliary_Sphere_Type",0.0],UNIT["Meter",1.0],AUTHORITY["EPSG",3857]]';






//微地图
var XMin = 12650034.107591387;
var YMin = 4144867.9283914543;
var XMax = 12652090.675101338;
var YMax = 4146109.4258633601;

function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return "";
}



var mapConfig = {
    origin: null,
    maxExtent: null,
    crs: null,
    center: null,
    init: function () {
        mapConfig.origin = new AIMap.Point(-20037508.342787001, 20037508.342787001);//中心点
        mapConfig.maxExtent = new AIMap.Bounds(new AIMap.Point(parseFloat(XMin), parseFloat(YMin)), new AIMap.Point(parseFloat(XMax), parseFloat(YMax)));
        mapConfig.crs = new AIMap.Proj.CRS(
            'EPSG:4490', '+proj=longlat +ellps=GRS80 +no_defs',
            {
                origin: [mapConfig.origin.x, mapConfig.origin.y],
                resolutions: [156543.03392800014,78271.516963999937,39135.758482000092,19567.879240999919,9783.9396204999593,4891.9698102499797,2445.9849051249898,1222.9924525624949,611.49622628137968,305.74811314055756,152.87405657041106,76.437028285073239,38.21851414253662,19.10925707126831,9.5546285356341549,4.7773142679493699,2.3886571339746849,1.1943285668550503,0.59716428355981721,0.29858214164761665,0.14929107082380833],
                bounds: mapConfig.maxExtent
            });
        mapConfig.center = mapConfig.maxExtent.getCenter(false);
        mapConfig.center = mapConfig.crs.unproject(mapConfig.center);
        mapConfig.center = [mapConfig.center.lat, mapConfig.center.lng];
        map = new AIMap.Map('map', { crs: mapConfig.crs, maxZoom: mapConfig.crs.options.resolutions.length - 1,minZoom:15, center: mapConfig.center, zoom: 18, attributionControl: false, zoomControl: false });
    }
};






$(function () {
    layerDirName = GetQueryString("SDPath");
    mapConfig.init();
    layer("daxuecheng", "true");
});

function layer(name, openFlag) {
    if (openFlag == "true") {
        try{
            var yxurl = new AIMap.TileLayer.ArcGIS(layerDirName + name + "/Layers/_alllayers/", {
                minZoom: 0,
                maxZoom: map.getMaxZoom(),
                format: 'png',
                transparent: true,
                opacity: 1.0,
                tileSize: 256,
                tileOrigin: mapConfig.origin,
                resolutions:mapConfig.crs.options.resolutions,
                maxExtent: mapConfig.crs.options.bounds
            });
            var _layer = { layer: yxurl, name: name ,type:'sl'};
            layerList.push(_layer);
            map.addLayer(yxurl);
        }
        catch(eee){
            console.log("-----------eeeee---" + eee);
        }
    }
    else {
        unloadLayer(name);
    }
}







var dingweiPolygon = null;
function mapClick(e, lat, lng) {
    removeLocation();
    lname = "";
    ll = [];
    xx = 2;
    if (layerList.length > 0 && layerList[layerList.length - 1].name != "wdq_yx2019") {
        var curPoint = null;
        if (e == null) {
            curPoint = [lng, lat];
        }
        else {
            lng = e.latlng.lng; lat = e.latlng.lat;
            curPoint = [lng, lat];
        }
        for (var f = 0; f < layerList.length; f++) {
            if (layerList[f].name != "cg_yx"//2018年正射影像
                          &&layerList[f].name != "cg_lmlw"//长葛路名路网
                          ) {
                          var res = "";
                          if (layerList[f].name == "cg_jianxinqu") {//建新区_2017及前
                              res = jxq2017q;
                          }
                    try{
                        for (var i = 0; i < res.features.length; i++) {
                            var geoJson = AIMap.geoJson(res.features[i].geometry);
                            var _lengthRes = AIMapPip.pointInLayer(curPoint, geoJson);
                            if (_lengthRes.length) {
                                removeLocation();
                                var zuobiaoArray = res.features[i].geometry.coordinates;
                                var lll = [];
                                for (var jj = 0; jj < zuobiaoArray.length; jj++) {
                                    var ll = [];
                                    var _tempArray = zuobiaoArray[jj];
                                    if(_tempArray.length == 1){
                                        _tempArray = _tempArray[0];
                                    }
                                    if(_tempArray instanceof Array){
                                        for (var j = 0; j < _tempArray.length; j++) {
                                            var p = new AIMap.LatLng(_tempArray[j][1], _tempArray[j][0], true);
                                            ll.push(p);
                                        }
                                    }
                                    lll.push(ll);
                                }
                                if(lll.length == 1){
                                    lll = lll[0];
                                }
                                dingweiPolygon = new AIMap.Polygon(lll, { fillOpacity: 0.3, fillColor: '#00FFFF', color: '#00FFFF', clickable: false, weight: 10 });
                                map.addLayer(dingweiPolygon);
                                var shuxing = res.features[i].properties;
                                var _returnstr = "{";
                                for (var key in shuxing) {
                                    _returnstr += "\"" + key + "\":\"" + shuxing[key] + "\",";
                                }
                                _returnstr = _returnstr.substr(0, _returnstr.length - 1);
                                _returnstr += "}";
                               switch (layerList[f].name) {
                                case "cg_jianxinqu": var name = "建新区"; break;
                                                          }
                                lname = lname + "-" + name;

                                break;
                            }else{
                                removeLocation();

                            }
                        }
                    }
                    catch(eeeee){
                    }
                }
        }
    }
    if (lname != "")
    { android.skip(lname, _returnstr); }
}




var name1 = "";
function mapDragEnd(e) {
    var _a1 = e.target.dragging._draggable._startPos;
    var _b1 = e.target.dragging._draggable._newPos;
    var _a = Math.abs(_a1.x - _b1.x);
    var _b = Math.abs(_a1.y - _b1.y);
    if (_a < 30 && _b < 30) {
        var _sreenPoint = e.target.dragging._draggable._startPoint;
        var test = map.containerPointToLayerPoint(_sreenPoint);
        var layerPoint = map.layerPointToLatLng(test);
        lat = layerPoint.lat; lng = layerPoint.lng;
        mapClick(null, layerPoint.lat, layerPoint.lng);
    }
}

function removeLocation() {
    if (dingweiPolygon && map.hasLayer(dingweiPolygon)) {
        map.removeLayer(dingweiPolygon);
        dingweiPolygon = null;
    }
}


function m_c() {
    measureClear();
}
function m() {
    measure();
}
function m_b() {
    measureBack();
}
function m_d() {
    measure_distance();
}
function m_e() {
    measureEnd();
}
function m_a() {
    measure_area();
}
function m_get() {
    getAccordinate();
}

function m_ed(x, y) {
    mapDragEnd(x, y);
}


//  模拟师范学院中心点
function setMapCenter(){

   map.setView(mapConfig.center, 17);
//     map.setView([4145344.261902, 12651567.898733], 17);

}




function loadLayerShp(name, checkFlag, color) {

    if(checkFlag){
     if(color == "" || color == undefined || color == null){
            color = "red";
        }
        data = eval(name);

        if (data != null && data.features.length > 0) {
            var cur_function_polys = [];
            var feature_length = data.features.length;
            try{
                for (var i = 0; i < feature_length; i++) {
                    var zuobiaoArray = data.features[i].geometry.coordinates;
                    var lll = [];
                    for (var jj = 0; jj < zuobiaoArray.length; jj++) {
                        var ll = [];
                        var _tempArray = zuobiaoArray[jj];
                        if(_tempArray.length == 1){
                            _tempArray = _tempArray[0];
                        }
                        if(_tempArray instanceof Array){
                            for (var j = 0; j < _tempArray.length; j++) {
                                var p = new AIMap.LatLng(_tempArray[j][1], _tempArray[j][0], true);
                                ll.push(p);
                            }
                        }
                        lll.push(ll);
                    }
                    if(lll.length == 1){
                        lll = lll[0];
                    }

                    var polyWeight = 0.8;
                    if(name == "crhb1126" ){
                        polyWeight = 2;
                    }
                    var pg = new AIMap.Polygon(lll, { weight: polyWeight, fillOpacity: 0.4, fillColor: color, color: "black", clickable: false });
                    cur_function_polys.push(pg);
                    map.addLayer(pg);
                }
                var _layer = { layer: cur_function_polys, name: name };
                layerList.push(_layer);
            }
            catch(eeee){
            }
        }
    }else{
        unloadLayer(name);
    }
}

function unloadLayer(name){
    removeLocation();
    for (var i = 0; i < layerList.length; i++) {
        if (layerList[i].name == name) {
            var _features = layerList[i].layer;
            if(_features instanceof Array){
                for (var j = 0; j < _features.length; j++) {
                    if (_features[j] != null && map.hasLayer(_features[j])) {
                        map.removeLayer(_features[j]);
                    }
                }
            }else{
                if (map.hasLayer(layerList[i].layer)) {
                    map.removeLayer(layerList[i].layer);
                }
            }
            layerList.splice(i, 1);
        }
    }
}



function fangda() {
    map.zoomIn();
}

function suoxiao() {
    map.zoomOut();
}


