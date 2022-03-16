import React from 'react';
import SearchItem from "./SearchItem.jsx";

const SearchButtonsPanel = (props) => {
    return (
        <div>
            {props.items.map((item) =>
                <SearchItem clickFunction={props.clickFunction}
                            item={item}
                            currentMode={props.currentMode}
                            key={item.id}
                            changeCounter={props.changeCounter}
                />
            )}
        </div>
    );
};

export default SearchButtonsPanel;