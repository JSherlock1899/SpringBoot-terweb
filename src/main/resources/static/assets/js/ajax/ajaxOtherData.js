//新建信息
$(document).on("click",".saveNewMsg",function(){
    // if(check()){
    //     alert('输入不合法！');
    //     return;
    // }
    var otherName = $('#otherName').val();
    var tname = $('#tname').val();
    var otherDate = $('#otherDate').val();
    var otherType = $('#otherType option:selected').val();
    var publisher = $('#publisher').val();
    var otherDescribe = $('#otherDescribe').val();
    $.ajax({
        url:"/other/insertOne",
        type:"post",
        datatype:"json",
        data:{
            "otherName" : otherName,
            "tname" : tname,
            "otherDate" : otherDate,
            "otherType" :otherType,
            "publisher" :publisher,
            "otherDescribe" : otherDescribe,
        },
        success : function(result){
            alert("新建成功，请等待管理员审核！");
            window.location.reload();
        },
        error:function(result){
            alert('请求出现错误...');
        }
    });
});


//修改信息
//获取修改后的信息
$(document).on("click",".save",function(){
    // if(check()){
    //     alert('输入不合法！');
    //     return;
    // }
    var otherName = $('#otherName').val();
    var tname = $('#tname').val();
    var otherDate = $('#otherDate').val();
    var otherType = $('#otherType option:selected').val();
    var publisher = $('#publisher').val();
    var otherDescribe = $('#otherDescribe').val();
    $.ajax({
        url:"/other/updateOne",
        type:"post",
        datatype:"json",
        data:{
            "otherName" : otherName,
            "tname" : tname,
            "otherDate" : otherDate,
            "otherType" :otherType,
            "publisher" :publisher,
            "otherDescribe" : otherDescribe,
        },
        success : function(result){
            alert("修改成功，请等待管理员审核！");
            window.location.reload();
        },
        error:function(result){
            alert('请求出现错误...');
        }
    });
});

//管理员修改信息
$(document).on("click",".alter",function(e,url){
    $("#myModalLabel").text("修改专利信息");
    $('#myModal').modal();
    $("#otherName").attr("value",$(this).closest("tr").find(".otherName").text());
    $("#tname").attr("value",$(this).closest("tr").find(".tname").text());
    $("#otherDate").attr("value",$(this).closest("tr").find(".otherDate").text());
    $("#otherType").attr("value",$(this).closest("tr").find(".otherType").text());
    $("#publisher").attr("value",$(this).closest("tr").find(".publisher").text());
    $("#otherDescribe").attr("value",$(this).closest("tr").find(".otherDescribe").text());
});

$(document).on("click",".alterSave",function(){
    if(check()){
        alert('输入不合法！');
        return;
    }
    var otherName = $('#otherName').val();
    var tname = $('#tname').val();
    var otherDate = $('#otherDate').val();
    var otherType = $('#otherType option:selected').val();
    var publisher = $('#publisher').val();
    var otherDescribe = $('#otherDescribe').val();
    $.ajax({
        url:"/other/alter",
        type:"post",
        datatype:"json",
        data:{
            "otherName" : otherName,
            "tname" : tname,
            "otherDate" : otherDate,
            "otherType" :otherType,
            "publisher" :publisher,
            "otherDescribe" : otherDescribe,
        },
        success : function(result){
            alert("修改成功！");
            window.location.reload();
        },
        error:function(result){
            alert('请求出现错误...');
        }
    });
});

//重新编辑按钮的事件
$(document).on("click","#btn_update",function () {
    $("#myModalLabel").text("重新编辑其他成果信息");
    $('#myModal').modal();
})

//新建按钮的事件
$(document).on("click","#btn_add",function () {
    $("#myModalLabel").text("新建其他成果信息");
    $('#myModal').modal();
})

//表单验证
function checkotherName(id,info){
    var uValue = document.getElementById(id).value.trim();
    if(!/^.{1,15}$/.test(uValue)){
        document.getElementById(id+"span").innerHTML="<font color='red' size='2'>"+info+"</font>";
        document.getElementById(id+"div").style.display="block";
        return true
    }else{
        document.getElementById(id+"span").innerHTML="<font color='green' size='3'>输入格式正确</font>";
        return false
    }
}

function showTips(id,info){
    document.getElementById(id+"span").innerHTML="<font color='gray' size='2'>"+info+"</font>";
}

function check() {
    var check = checkotherName('otherName','请按要求输入查询的名称');
    return check;
}

// 审核通过
$(document).on("click","#pass",function(){
    var otherName = $('.otherName').text();
    var message  = $('#message').val();
    $.ajax({
        url:"/other/pass",
        type:"post",
        datatype:"json",
        data:{
            "majorkey" : otherName,
            "message" : message,
        },
        success : function(result){
            alert("操作成功");
            window.location.reload();
        },
        error:function(result){
            alert('请求出现错误...');
        }
    })
})

// 审核不通过
$(document).on("click","#nopass",function(){
    var otherName = $('.otherName').text();
    var message  = $('#message').val();
    $.ajax({
        url:"/other/nopass",
        type:"post",
        datatype:"json",
        data:{
            "majorkey" : otherName,
            "message" : message,
        },
        success : function(result){
            alert("操作成功");
            window.location.reload();
        },
        error:function(result){
            alert('请求出现错误...');
        }
    })
})

//上传文件

$(function () {
    initFileInput("uploadfile");
})

function initFileInput(ctrlName) {
    var control = $('#' + ctrlName);
    var otherName = $('#otherName').val();
    control.fileinput({
        language: 'zh', //设置语言
        uploadUrl: "/file/upload?model=other&name=" + otherName + "&majorkey=" + otherName, //上传的地址
        allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀
        //uploadExtraData:{"id": 1, "fileName":'123.mp3'},
        uploadAsync: true, //默认异步上传
        showUpload: true, //是否显示上传按钮
        showRemove : true, //显示移除按钮
        showPreview : true, //是否显示预览
        showCaption: false,//是否显示标题
        browseClass: "btn btn-primary", //按钮样式
        dropZoneEnabled: true,//是否显示拖拽区域
        //minImageWidth: 50, //图片的最小宽度
        //minImageHeight: 50,//图片的最小高度
        //maxImageWidth: 1000,//图片的最大宽度
        //maxImageHeight: 1000,//图片的最大高度
        //maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
        //minFileCount: 0,
        //maxFileCount: 10, //表示允许同时上传的最大文件个数
        enctype: 'multipart/form-data',
        validateInitialCount:true,
        previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",

    }).on('filepreupload', function(event, data, previewId, index) {     //上传中
        var form = data.form, files = data.files, extra = data.extra,
            response = data.response, reader = data.reader;
        console.log('文件正在上传');
    }).on("fileuploaded", function (event, data, previewId, index) {    //一个文件上传成功
        console.log('文件上传成功！'+data.id);

    }).on('fileerror', function(event, data, msg) {  //一个文件上传失败
        console.log('文件上传失败！'+data.id);


    })
}

$(document).on("click",".delete",function(e,url){
    var majorkey = $(this).closest("tr").find(".otherName").text();
    var boolean = todel();
    if (!boolean)
        return
    $.ajax({
        url:"/other/delete",
        type:"post",
        datatype:"json",
        data:{
            "majorkey" : majorkey
        },
        success : function(msg){
            alert("删除成功");
            $(e.target).closest("tr").fadeOut();
            window.location.reload();
        },
        error:function(msg){
            alert('请求出现错误...');
        }
    });
});