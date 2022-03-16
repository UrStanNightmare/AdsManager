import React from 'react';
import {Button} from "react-bootstrap";

const ButtonBlock = (props) => {
    return (
        <div>
            {props.dataVal.category.map((catButton, id) => (
                <Button
                    key={id}
                    id={`button-${id}`}
                    type="button"
                    variant="outline-dark"
                    onClick={event => {
                        props.removeCat(catButton)
                    }}
                >
                    {catButton.name}
                </Button>
            ))}
        </div>
    );
};

export default ButtonBlock;