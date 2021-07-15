import React, { FC, useState, useEffect, useRef } from "react";
import "./Carousel.css";

type actionType = "prev" | "next";
export interface CarouselProps {
  defaultActive?: number;
  interval?: number;
  children: React.ReactElement[];
}

export const Carousel: FC<CarouselProps> = ({
  children,
  defaultActive = 1,
  interval,
}) => {
  const dotWrapperWidth = 60;
  const dotWidthInterval = interval ? interval / dotWrapperWidth : 50;

  const SCREEN_WIDTH = window.screen.width;
  const [active, setActive] = useState(defaultActive);

  const defaultTransform = "translateX(0)";
  const [transform, setTransform] = useState(defaultTransform);
  const setTransition = (currentctive: number) => {
    const distance = (1 - currentctive) * SCREEN_WIDTH;
    setTransform(`translateX(${distance}px)`);
  };

  const defaultDotWidth = 0;
  const [dotWidth, setDotWidth] = useState(defaultDotWidth);

  const changeActive = (action: actionType) => {
    let currentActive;
    if (action === "prev") {
      currentActive = active === 1 ? children.length : active - 1;
    } else {
      currentActive = active === children.length ? 1 : active + 1;
    }
    setActive(currentActive);
    setTransition(currentActive);
  };

  useEffect(() => {
    let slickTimer = 0;
    slickTimer = setInterval(() => {
      changeActive("next");
    }, interval);
    let dotWidth = 0;
    const timer = setInterval(() => {
      dotWidth++;
      if (dotWidth === dotWrapperWidth) {
        clearInterval(timer);
      }
      setDotWidth(dotWidth);
    }, dotWidthInterval);
    return () => {
      clearInterval(slickTimer);
    };
  }, [active]);

  return (
    <div className="carousel">
      <div className="container" style={{ transform }}>
        {children.map((child, index) => (
          <div
            key={child.key}
            style={{ left: index * SCREEN_WIDTH }}
            className="item"
          >
            {child}
          </div>
        ))}
      </div>
      <ul className="slick">
        {children.map((child, index) => (
          <li key={child.key} className="slick-dots">
            {active === index + 1 && <p style={{ width: dotWidth }}></p>}
          </li>
        ))}
      </ul>
    </div>
  );
};

Carousel.defaultProps = {
  interval: 3000,
};

export default Carousel;
