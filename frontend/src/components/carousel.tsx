import React, { useState, useEffect } from 'react';

import './carousel.css';
import { Carousel as BSCarousel, ProgressBar } from 'react-bootstrap';

function Carousel() {
	const [activeIndex, setActiveIndex] = useState(0);
	const [interval] = useState(3000);
	var progresses = [0, 0, 0];
	const [now, setNow] = useState(progresses);
	const [timerActive, setTimerActive] = useState(true);
	var timer: any;

	function startTimer() {
		const timeOut = 300;

		timer = setInterval(() => {
			let percent = now[activeIndex] + timeOut / interval * 100;
			progresses[activeIndex] = percent;
			progresses = [...progresses];
			setNow(progresses);

		  if(now[activeIndex] >= 100) {
	 			stopTimer();
	 			let index = activeIndex;
	 			index++;
	 			if(index > 2) {
	 				index = 0;
	 			}
	 			setActiveIndex(index);
	 		}
		}, timeOut);

		return timer;
	}

	function stopTimer() {
		clearInterval(timer);
		progresses[activeIndex] = 0;
		setNow(progresses);
		// setNow((now) => {
		// 	now[activeIndex] = 0;
		// 	return now;
		// });
		setTimerActive(false);
	}

	const handleSlid = (index: number, direction: string) => {
		// console.log(index, direction);
		// setNow(progresses[index]);
		setTimerActive(true);
	};

	const onSelect = (index: number) => {
		// console.log(index);
		stopTimer();
		// setNow(progresses[index]);
		setActiveIndex(index);
		setTimerActive(true);
	}

	useEffect(() => {
		if(timerActive) {
			startTimer();
		}
	  return () => clearInterval(timer);
	});

  return (
  	<div>
	    <BSCarousel controls={false} interval={interval} indicators={false} activeIndex={activeIndex} onSlid={handleSlid}>
			  <BSCarousel.Item className="iphone">
			    <BSCarousel.Caption className="caption">
			    	<h1>xPhone</h1>
		      	<p>
		      		Lots to love. Less to spend. <br/>
		      		Starting at $399.
	      		</p>
			    </BSCarousel.Caption>
			  </BSCarousel.Item>
			  <BSCarousel.Item className="tablet">
			    <BSCarousel.Caption className="caption black-font">
			      <h1>Tablet</h1>
			      <p>Just the right amount of everything.</p>
			    </BSCarousel.Caption>
			  </BSCarousel.Item>
			  <BSCarousel.Item className="airpods">
			    <BSCarousel.Caption className="caption black-font">
			      <h1>Buy a Tablet or xPhone for college. <br/> Get arPods</h1>
			    </BSCarousel.Caption>
			  </BSCarousel.Item>
			</BSCarousel>

    	<ol className="progress-bars">
    		<li>
    			<ProgressBar now={now[0]} onClick={() => onSelect(0)} />
    		</li>
    		<li>
    			<ProgressBar now={now[1]} onClick={() => onSelect(1)} />
    		</li>
    		<li>
    			<ProgressBar now={now[2]} onClick={() => onSelect(2)} />
    		</li>
    	</ol>
		</div>
  );
}

export default Carousel;
