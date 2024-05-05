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

/*
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
*/

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
