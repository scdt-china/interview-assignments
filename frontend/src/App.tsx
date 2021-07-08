import React, { useState, useEffect } from 'react';
import './App.css';

const IMAGELIST = [
  { text: '图片1', color: '#fcf', id: 1 },
  { text: '图片2', color: '#cfc', id: 2 },
  { text: '图片3', color: '#fcc', id: 3 }
];
const minWidth = 500;

const Carousel = () => {
  const [index, setIndex] = useState(0);
  const [posLeft, setPosLeft] = useState(0);
  const [timmer, setTimmer] = useState(null);

  const getPointList = () => {
    const pointArr = [];

    for (let pointIndedx = 0; pointIndedx < IMAGELIST.length; pointIndedx++) {
      let pointDom = (
        <span
          className={`point ${pointIndedx === index ? 'active' : ''}`}
          key={Math.random()}
        >
        </span>
      );
      pointArr.push(pointDom);
    }
    return pointArr;
  }

  const start = () => {
    clearInterval(timmer);
    let _timmer = setInterval(() => {
      turn('left');
    }, 1000);
    setTimmer(_timmer);
  }

  const stop = () => {
    clearInterval(timmer)
    setTimmer(null);
  }

  const turn = (direction: string) => {
    let _index = index;
    if (direction === 'left') {
      _index = _index + 1;
    } else if (direction === 'right') {
      _index = index - 1;
    }

    if (_index > IMAGELIST.length - 1) {
      _index = 0;
    }

    if (_index < 0) {
      _index = IMAGELIST.length - 1;
    }

    setIndex(_index);
    setPosLeft(-minWidth * _index)
  }

  useEffect(() => {
    start();
  }, [index]);

  return (
    <div className="App"
      onMouseOver={() => stop()}
      onMouseOut={() => start()}
    >
      <div
        className="banner"
        style={{
          width: minWidth * IMAGELIST.length,
          left: posLeft
        }}
      >
        {IMAGELIST.map(item => (
          <div
            className="banner-image"
            key={item.id}
            style={{
              width: minWidth,
              background: item.color,
            }}
          >
            {item.text}
          </div>
        ))}
      </div>
      <div className="left" onClick={() => turn('right')}>{'<'}</div>
      <div className="right" onClick={() => turn('left')}>{'>'}</div>
      <div className="point-list">
        {getPointList()}
      </div>
    </div>
  );
}

export default Carousel;
