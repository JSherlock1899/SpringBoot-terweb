/**
 * 实现下拉框的值改变，查询的结果也改变
 */

function goSelect(model) {
			var cname = $('#college option:selected').val();// 选中的学院值
			var dname = $('#sdept option:selected').val();// 选中的专业值
			var starttime = $('#starttime').val();
			var endtime = $('#endtime').val();
			var tname = $("#Tname").val();
			if (tname == null || tname == undefined || tname == 'null' || tname == 'undefined') {
				tname = '';
			}
			var tpye = $("#type").val();
			var url = "/" + model + "/" + tpye +"?cname=" + cname + "&dname=" + dname + "&starttime=" + starttime + "&endtime=" + endtime + "&tname=" + tname;
            open(url,"_self");

	}



		//重置下拉框和文本框
		function resetSelect(){
			$("select").find("option:first").prop("selected",true);
			$('input').val("");
		}

		//回到顶部
		function backTop() {
            window.scrollTo(0,0);
        }