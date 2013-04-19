function forgetPassword(){
	if($("input[name='checkbox-2a']").attr('checked')=='checked'){
			localStorage.setItem('username', $("#name").val()); 
			localStorage.setItem('password', $("#password").val()); 
	}
	else {
		localStorage.removeItem('username');
		localStorage.removeItem('password');
	}
}
