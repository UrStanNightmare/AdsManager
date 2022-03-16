import React, {useState} from "react";
import './styles/App.css';
import MenuContainer from "./component/UI/MenuContainer.jsx";
import ContentContainer from "./component/UI/ContentContainer.jsx";

function App() {
    const [modeValue, setModeValue] = useState('1');

    const modes = [
        {name: 'Banners', value: '1'},
        {name: 'Categories', value: '2'}
    ];

    const [curCatNumber, setCurCatNumber] = useState(0)

    const changeMode = (value) => {
        setModeValue(value)
        setCurCatNumber(0)
    }

    return (
        <div className="App">
                <MenuContainer menuButtons={modes}
                               change={changeMode}
                               currentMod={modeValue}
                               setCurCatNumber={setCurCatNumber}
                               getCurCatNumber={curCatNumber}
                />
                <ContentContainer
                    currentMode={modeValue}
                    setCurCatNumber={setCurCatNumber}
                    getCurCatNumber={curCatNumber}
                />
        </div>
    );
}

export default App;