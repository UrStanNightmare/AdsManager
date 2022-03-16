import React, {useState} from 'react';

import '../../../styles/UI/subPanel/EditPanelBanner.css';
import {Alert, Button, Col, Dropdown, Form, InputGroup, Row} from "react-bootstrap";
import ButtonBlock from "../../buttons/ButtonBlock.jsx";

const EditPanelBanner = (props) => {

    const updateData = (value) => {
        props.updateDataFunc({...props.dataVal, category: [...props.dataVal.category, value]})
        props.setCurCats(1)
    }

    const addCat = (value) => {
        if (props.dataVal.category === 'undefined') {
            props.updateDataFunc({...props.dataVal, category: [value]})
            props.setCurCats(1)
        } else {
            !props.dataVal.category.some(category => category.name === value.name)
                ?
                updateData(value)
                :
                showAlert({text: "Category \'" + value.name + "\' already signed!"})
        }
    }

    const removeCat = (value) => {
        props.updateDataFunc({
            ...props.dataVal,
            category: props.dataVal.category
                .filter(value1 => value1.name !== value.name)
        })
    }

    const [show, setShow] = useState(false);
    const [alertText, setAlertText] = useState("")

    const showAlert = (alert) => {
        setAlertText(alert.text)
        setShow(true)
    }

    const saveClick = () => {
        let errorData = "";
        let errorCounter = 0

        if (props.dataVal.name === "") {
            errorData = errorData + "name, "
            errorCounter++
        }
        if (props.dataVal.price === "") {
            errorData = errorData + "price, "
            errorCounter++
        }
        if (props.dataVal.category.length === 0) {
            errorData = errorData + "category, "
            errorCounter++
        }
        if (props.dataVal.text === "") {
            errorData = errorData + "text, "
            errorCounter++
        }
        if (errorCounter < 1) {
            const response = props.sendBannerFunc().then((result)=>{
                if (result.data !== ""){
                    showAlert({text: result.data})
                }

            })

        } else {
            showAlert({text: "Incorrect input data! [" + errorData + "]"})
        }

    }

    const deleteFunc = () => {
        if (props.dataVal.name.length < 1){
            showAlert({text: "Can't delete an empty banner!!!"})
        }else {
            const response = props.deleteBannerFunc().then((result)=>{
                if (result.data !== ""){
                    showAlert({text: result.data})
                }

            })
        }
    }

    return (
        <div className="EditPanel">
            <div className="EditLabelBlock">
                <h2 style={{marginLeft: '5px'}}>{props.label}</h2>
            </div>

            <div className="EditFormBlock">
                <Form className="EditForm">
                    <Form.Group as={Row} className="mb-3" controlId="formPlaintextName">
                        <Form.Label column sm="2">
                            Name
                        </Form.Label>
                        <Col sm="10">
                            <Form.Control type="text" placeholder="Banner name"
                                          value={props.dataVal.name}
                                          onChange={event => props.updateDataFunc({
                                              ...props.dataVal,
                                              name: event.target.value
                                          })}/>
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} className="mb-3" controlId="formPlaintextPrice">
                        <Form.Label column sm="2">
                            Price
                        </Form.Label>
                        <Col sm="10">
                            <Form.Control type="text" placeholder="Price"
                                          value={props.dataVal.price}
                                          onChange={event => props.updateDataFunc({
                                              ...props.dataVal,
                                              price: event.target.value
                                          })}/>
                        </Col>
                    </Form.Group>

                    <InputGroup className="mb-3">
                        <Form.Label column sm="2">
                            Category
                        </Form.Label>

                        {props.dataVal.category !== 'undefined' && props.dataVal.category.length > 0
                            ? <ButtonBlock dataVal={props.dataVal}
                            removeCat={removeCat}
                            />
                            : null
                        }



                        < Dropdown style={{alignSelf: "flex-end"}}>
                            <Dropdown.Toggle variant="secondary" id="dropdown-basic"/>

                            <Dropdown.Menu>
                        {props.getCategories.map((catButton, id) => (
                            <Dropdown.Item key={id} onClick={event => {
                            addCat(catButton)
                        }}>
                        {catButton.name}
                            </Dropdown.Item>
                            ))}

                            </Dropdown.Menu>
                            </Dropdown>
                            </InputGroup>

                            <Form.Group as={Row} className="mb-3" controlId="formPlaintextText">
                            <Form.Label column sm="2">
                            Text
                            </Form.Label>
                            <Col sm="10">
                            <Form.Control as="textarea" placeholder="Banner text" style={{maxHeight: '58vh'}}
                            value={props.dataVal.text}
                            onChange={event => props.updateDataFunc({
                            ...props.dataVal,
                            text: event.target.value
                        })}/>
                            </Col>
                            </Form.Group>
                            </Form>
                            </div>

                            <div className="AlertBlock">
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

                            <div className="EditButtonsBlock">
                            <Button variant="outline-dark"
                            className="EditButtonStandard"
                            onClick={saveClick}>
                            Save
                            </Button>
                            <Button variant="outline-dark"
                            className="EditButtonStandard"
                            onClick={event => deleteFunc()}>
                            Delete
                            </Button>
                            </div>
                            </div>
                            );
                        };

                        export default EditPanelBanner;