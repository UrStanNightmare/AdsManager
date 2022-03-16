import React, {useState} from "react";

import {ButtonGroup, ToggleButton} from "react-bootstrap";

import '../../styles/UI/MenuContainer.css'
import '../../styles/MenuButton.css'
import 'bootstrap/dist/css/bootstrap.min.css';

const MenuContainer = (props) => {
    return (
        <div className="MenuContainer">
            <ButtonGroup>
                {props.menuButtons.map((radio, idx) => (
                    <ToggleButton
                        className="MenuButton"
                        key={idx}
                        id={`radio-${idx}`}
                        type="radio"
                        variant="outline-dark"
                        name="radio"
                        value={radio.value}
                        checked={props.currentMod === radio.value}
                        onChange={(e) => props.change(e.currentTarget.value)}
                    >
                        {radio.name}
                    </ToggleButton>
                ))}
            </ButtonGroup>
        </div>
    );
};

export default MenuContainer;