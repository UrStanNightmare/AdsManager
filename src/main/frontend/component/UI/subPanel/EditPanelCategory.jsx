import React, {useState} from 'react';

import '../../../styles/UI/subPanel/EditPanelCategory.css'
import {Alert, Button, Col, Form, Row} from "react-bootstrap";

const EditPanelCategory = (props) => {
    const [show, setShow] = useState(false);
    const [alertText, setAlertText] = useState("")

    const showAlert = (alert) => {
        setAlertText(alert.text)
        setShow(true)
    }

    const saveClick = () => {
        let errorData = '';
        let errorCounter = 0
        if (props.dataVal.name === "") {
            errorData = errorData + "name, "
            errorCounter++
        }
        if (props.dataVal.requestId === "") {
            errorData = errorData + "requestId, "
            errorCounter++
        }

        if (errorCounter < 1) {
            const response = props.sendCategoryFunc().then((result)=>{
                if (result.data !== ""){
                    showAlert({text: result.data})
                }

            })

        } else {
            showAlert({text: "Incorrect input data! [" + errorData + "]"})
        }

    }

    const markToDelete = () => {
        if (props.dataVal.name.length < 1){
            showAlert({text: "Can't delete an empty category!!!"})
        }else {
            const response = props.deleteCategoryFunc().then((result)=>{
                if (result.data !== ""){
                    showAlert({text: result.data})
                }

            })
        }
    }

    return (
        <div className="EditCatPanel">
            <div className="EditCatLabelBlock">
                <h2 style={{marginLeft: '5px'}}>{props.dataVal.name} {props.label} {props.dataVal.id}</h2>
            </div>

            <div className="EditCatFormBlock">
                <Form className="EditCatForm">
                    <Form.Group as={Row} className="mb-3" controlId="formPlaintextName">
                        <Form.Label column sm="2">
                            Name
                        </Form.Label>
                        <Col sm="10">
                            <Form.Control type="text"
                                          placeholder="Category name"
                                          value={props.dataVal.name}
                                          onChange={event => props.updateDataFunc({
                                              ...props.dataVal,
                                              name: event.target.value
                                          })}
                            />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} className="mb-3" controlId="formPlaintextPrice">
                        <Form.Label column sm="2">
                            Request ID
                        </Form.Label>
                        <Col sm="10">
                            <Form.Control type="text"
                                          placeholder="RequestId"
                                          value={props.dataVal.requestId}
                                          onChange={event => props.updateDataFunc({
                                              ...props.dataVal,
                                              requestId: event.target.value
                                          })}
                            />
                        </Col>
                    </Form.Group>
                </Form>
            </div>

            <div className="AlertCatBlock">
                {show
                    ? (<Alert variant="danger"
                              style={{maxHeight: '5vh', marginLeft: '5vw', marginRight: '5vw'}}
                              onClose={() => setShow(false)} dismissible>
                        <p>
                            {alertText}
                        </p>
                    </Alert>)
                    : null}

            </div>

            <div className="EditCatButtonsBlock">
                <Button variant="outline-dark"
                        className="EditButtonStandard"
                        onClick={saveClick}
                >
                    Save
                </Button>
                <Button variant="outline-dark"
                        className="EditButtonStandard"
                        onClick={event => markToDelete()}
                >
                    Delete
                </Button>
            </div>
        </div>
    );
};

export default EditPanelCategory;