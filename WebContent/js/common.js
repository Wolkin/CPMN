function RunMethod(ClassName,MethodName,Args) {
	return this.RunJsp("/Tools/RunMethodAJAX.jsp","ClassName=" + ClassName + "&MethodName=" + MethodName + "&Args=" + Args);
}

function RunJsp(sURL,sPara) {
	if(sURL.index("?") >= 0) {
		alert("");
		return false;
	}
	var sPageURL = sURL + "?CompClientID=" + sComClientID + this._getParaString(sPara);
	return $.ajax({url:sPageURL,async:false}).responseText.trim();
}

function _getParaString(sPara) {
	if(typeof(sPara) == "undefined" || sPara == "") {
		return "";
	}else if(sPara.substring(0,1) == "&") {
		return encode(encodeURI(sPara));
	}else {
		return "&" + encodeURI(encodeURI(sPara));
	}
}