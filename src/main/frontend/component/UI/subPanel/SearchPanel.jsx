import React, {useEffect, useState} from 'react';

import '../../../styles/UI/subPanel/SearchPanel.css'
import {Button, FormControl, Image, InputGroup} from "react-bootstrap";
import SearchButtonsPanel from '../../SearchButtonsPanel.jsx';

const SearchPanel = (props) => {
    const [searchString, setSearchString] = useState('')

    const performSearch = (value) => {
        setSearchString(value)
    }

    const updateData = () => {
        const bannerResponse = props.updateBannerFunc()
        const categoryResponse = props.updateCategoriesFunc()
    }

    useEffect(() => {
        props.currentMode === '1'
            ?
            updateData()
            :
            null
    }, [searchString, props.currentMode])

    return (
        <div className="SearchPanel">
            <div className="LabelBlock">
                <h2>{props.type.plural + ":"}</h2>
            </div>
            <div className="SearchBlock">
                <InputGroup className="mb-3" bsPrefix="Search-group">
                    <div className="ImageDiv">
                        <Image src='https://upload.wikimedia.org/wikipedia/commons/2/22/Google_Magnifying_Glass.svg'/>
                    </div>
                    <FormControl
                        placeholder={"Enter " + props.type.name.toLowerCase() + " name..."}
                        value={searchString}
                        onChange={event => performSearch(event.target.value)}
                    />
                </InputGroup>
            </div>
            <div className="ContentBlock">
                {props.currentMode === '1'
                    ?
                    (
                        <SearchButtonsPanel
                            changeCounter={props.updateCurCatCounter}
                            clickFunction={props.setEditObject}
                            currentMode={props.currentMode}
                            items={[...props.getBanners]
                                .filter(item => item.name.includes(searchString))
                                .sort((a, b) => a.name.localeCompare(b.name))
                            }/>)
                    :
                    (
                        <SearchButtonsPanel
                            changeCounter={props.updateCurCatCounter}
                            clickFunction={props.setEditObject}
                            currentMode={props.currentMode}
                            items={[...props.getCategories]
                                .filter(item => item.name.includes(searchString))
                                .sort((a, b) => a.name.localeCompare(b.name))
                            }/>
                    )
                }

            </div>
            <div className="CreateButtonBlock">
                <Button variant="outline-dark" className="CreateButton"
                        onClick={event=> props.clearInputFunc()}>{"Create new " + props.type.name}</Button>
            </div>
        </div>
    );
};

export default SearchPanel;