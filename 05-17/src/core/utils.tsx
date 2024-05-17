export const emailValidator = (email: string) => {
    const re = /\S+@\S+\.\S+/;
  
    if (!email) return "Email을 입력해주세요.";
    if (!re.test(email)) return "올바른 이메일 형식이 아닙니다.";
  
    return '';
  };
  
  export const passwordValidator = (password: string) => {
    if (!password) return "비밀번호를 입력해주세요.";
    if (password.length < 6) return "비밀번호는 최소 6자 이상이어야 합니다.";
  
    return '';
  };
  