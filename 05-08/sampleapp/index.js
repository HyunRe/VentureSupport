import React from 'react';
//import ReactDOM from 'react-dom';
//import './index.css';
import { name as appName } from './app.json'; // 애플리케이션의 이름을 가져옵니다.
import App from './App';
import reportWebVitals from './reportWebVitals';
import { AppRegistry } from 'react-native';

// 앱 컴포넌트
const RNApp = () => (
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

// 앱 등록
AppRegistry.registerComponent(appName, () => RNApp);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
/*{const root = ReactDOM.createRoot(document.getElementById('root'));
//ReactDOM.createRoot()를 사용하는 것은 React의 Concurrent Mode를 활성화하는 데 사용됩니다.
//그러나 보통은 단순히 ReactDOM.render()를 사용하여 애플리케이션을 렌더링합니다.}*/
/*root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

// 네이버 로그인 초기화
NaverLogin.initialize({
  appName,
  consumerKey,
  consumerSecret,
  serviceUrlScheme,
  disableNaverAppAuth: true,
});

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);
*/
// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
