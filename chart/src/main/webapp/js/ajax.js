function createxmlHttpRequest() { 
    var xmlHttp = null; 
    if (window.ActiveXObject) { 
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP"); 
    } else if (window.XMLHttpRequest) { 
        xmlHttp=new XMLHttpRequest(); 
    } 
    return  xmlHttp;
}

function doGet(url,callback){ 
        // 注意在传参数值的时候最好使用encodeURI处理一下，以防出现乱码 
       var xmlHttp = createxmlHttpRequest(); 
        xmlHttp.open("GET",url); 
        xmlHttp.send(null); 
        xmlHttp.onreadystatechange = function() { 
        if ((xmlHttp.readyState == 4) && (xmlHttp.status == 200)) { 
             var  result =  xmlHttp.responseText;
             callback(result); 
        }
    } 
} 

function doPost(url,data,callback){ 
        // 注意在传参数值的时候最好使用encodeURI处理一下，以防出现乱码 
        var xmlHttp = createxmlHttpRequest(); 
        xmlHttp.open("POST",url); 
        xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); 
        xmlHttp.send(data); 
        xmlHttp.onreadystatechange = function() { 
        if ((xmlHttp.readyState == 4) && (xmlHttp.status == 200)) { 
            callback('success');
        } 
    } 
} 