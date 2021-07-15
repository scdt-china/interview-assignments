import React from 'react';
import { render } from '@testing-library/react';
import App from './App';
import Carousel from './components/Carousel';

import img1 from './assets/iphone.png';
import img2 from './assets/tablet.png';
import img3 from './assets/airpods.png';

const imgs = [
  { title: 'xPhone', desc: 'Lots to love. Less to spend.', desc2: 'Starting at $399', bg: img1, bgColor: '#111', textColor: '#fff' },
  { title: 'Tablet', desc: 'Just the right amount of everything', bg: img2, bgColor: '#fafafa', textColor: '#333' },
  { title: 'Buy a Tablet or xPhone for college.', title2: 'Get airPods.', bg: img3, bgColor: '#f1f1f3', textColor: '#333' },
];

describe('test App', () => {
  it('should render Carousel', () => {
    const wrapper = render(
      <Carousel>
        {
          imgs.map((item, index) => (
            <div key={index} className="wapper" style={{
              backgroundImage: `url(${item.bg})`,
              backgroundColor: item.bgColor,
            }}>
              <div className="info" style={{color: item.textColor}}>
                <h1>{item.title}</h1>
                {item.title2 && <h2>{item.title2}</h2>}
                {item.desc && <p>{item.desc}</p>}
                {item.desc2 && <p>{item.desc2}</p>}
              </div>
            </div>
          ))
        }
      </Carousel>
    )
    const element = wrapper.getByText('xPhone');
    expect(element).toBeInTheDocument();
  })
})
