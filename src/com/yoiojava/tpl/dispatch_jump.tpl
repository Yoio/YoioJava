<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${PROMPT_JUMP}</title>
<style type="text/css">
*{ padding: 0; margin: 0; }
body{ background: #fff; font-family: "Microsoft YaHei"; color: #333; font-size: 16px; }
.system-message{ padding: 24px 48px; }
.system-message h1{ font-size: 100px; font-weight: normal; line-height: 120px; margin-bottom: 12px; }
.system-message .jump{ padding-top: 10px}
.system-message .jump a{ color: #333;}
.system-message .success,.system-message .error{ line-height: 1.8em; font-size: 36px }
.system-message .detail{ font-size: 12px; line-height: 20px; margin-top: 12px; display:none}

* { margin:0; padding:0; }
.l { float:left; }
.r { float:right; }
body { font-size:12px; font-family: "Microsoft YaHei"; background:#f4f4f4; }
.msgBox { width:350px; box-shadow:0px 0px 10px #c0c0c0; -moz-box-shadow:0px 0px 10px #c0c0c0; -ms-box-shadow:0px 0px 10px #c0c0c0; -o-box-shadow:0px 0px 10px #c0c0c0; -webkit-box-shadow:0px 0px 10px #c0c0c0; position:absolute; top:40%; left:50%; margin-top:-100px; margin-left:-175px;background: #fff; border:1px solid #cbcbcb; overflow: hidden;}
.head { padding:5px 10px; height: 20px; line-height: 20px; background: #f0f0f0; border-bottom: 1px solid #cbcbcb; color: #999; }
.content { min-height:20px; padding:10px; margin-top: 5px;/*margin-top: 45px;*/ font-size: 18px; color: #063; text-align: center;}
#rightContent{color: #063;}
#rightContent i.right{padding:15px 24px; background-image: url(right.gif); background-repeat: no-repeat; background-position: 0 center;}
.msgBox .foot { text-align: center; color: #999; word-spacing: 0px; letter-spacing: 1px; height: 20px; line-height: 20px; }
.msgBox .letter {height: 30px; line-height: 20px; }
.msgBox .foot a,#dolrContent p.link a{ font-size:12px; text-decoration: none; color: #999; }
.msgBox .foot a:hover, #dolrContent p.link a:hover{ color:#777; }
#errorContent{color: #f00;}
#errorContent i.error{padding:15px 24px; background-image: url(error.gif); background-repeat: no-repeat; background-position: 0 center;}
</style>
</head>
<body>

    <div class="msgBox">
        <div class="head">
            <span class="l">${THE_SYSTEM_PROMPTS}</span>
        </div>
        <#if flag == "success">
        <div id="rightContent" class="content"><i class="right">:) &nbsp;</i>${msg}</div>
        <#else>
        <div id="errorContent" class="content"><i class="error">:( &nbsp;</i>${msg}</div>
        </#if>
        <div id="foot" class="foot letter">
${AUTOMATIC_PAGE} <a id="href" href="${url}">${JUMP}</a> ${WAITING_TIME}： <b id="wait">${waitSecond}</b>
        </div>
    </div>


<script type="text/javascript">
(function(){
var wait = document.getElementById('wait'),href = document.getElementById('href').href;
var interval = setInterval(function(){
	var time = --wait.innerHTML;
	if(time <= 0) {
		if(href.indexOf("history") >= 0){
			history.back();
		}else{
			location.href = href;
		}
		clearInterval(interval);
	};
}, 1000);
})();
</script>
</body>
</html>