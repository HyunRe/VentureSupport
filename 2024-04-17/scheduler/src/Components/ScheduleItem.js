// ScheduleItem.js
import React from 'react';
import './ScheduleItem.css'; // 스타일 파일 임포트

const ScheduleItem = ({ title, time }) => {
  return (
    <div className="schedule-item">
      <div className="time">{time}</div>
      <div className="content">
        <div className="title">{title}</div>
      </div>
    </div>
  );
};

export default ScheduleItem;
