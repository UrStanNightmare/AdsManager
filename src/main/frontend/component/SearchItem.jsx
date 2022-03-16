import React from 'react';

import '../styles/SearchItem.css'
import {Button} from "react-bootstrap";

const SearchItem = (props) => {
    const updateBannerData = () => {
        if (props.currentMode === '1') {
            props.clickFunction(
                {
                    id: props.item.id,
                    name: props.item.name,
                    price: props.item.price,
                    category: props.item.category,
                    text: props.item.text,
                    requestId: ''
                }
            )
            props.changeCounter(1)
        } else {
            props.clickFunction(
                {
                    id: props.item.id,
                    name: props.item.name,
                    price: '',
                    category: '',
                    text: '',
                    requestId: props.item.requestId
                }
            )
            props.changeCounter(1)
        }
    }

    return (

        <div className="ItemButton">
            <Button variant="light"
                    onClick={event => updateBannerData()}>
                {props.item.name}
            </Button>
        </div>
    );
};

export default SearchItem;