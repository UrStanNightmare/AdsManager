import React, {useEffect, useState} from 'react';

import '../../styles/UI/ContentContainter.css'
import SearchPanel from "./subPanel/SearchPanel.jsx";
import EditPanelCategory from "./subPanel/EditPanelCategory.jsx";
import EditPanelBanner from "./subPanel/EditPanelBanner.jsx";
import configData from "../../config/config.json";
import axios from "axios";

const ContentContainer = (props) => {
    const [bannersData, setBannersData] = useState([])
    const [categoriesData, setCategoriesData] = useState([])


    useEffect(() => {
        clearInputData()
    }, [props.currentMode])


    const [editObjectData, setEditObjectData] = useState({
        id: '0',
        name: '',
        price: '',
        category: [],
        text: '',
        requestId: ''
    })

    const axiosConfig = {
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
            'Access-Control-Allow-Origin': '*',
        },
        auth: {
            username: configData.ADMIN_USERNAME,
            password: configData.ADMIN_PASSWORD
        }
    }

    async function updateBanners(){
        const response = await axios.get(configData.LOCAL_HOST_URL + "/banners", axiosConfig)
        setBannersData(response.data)

        return response
    }

    async function updateCategories(){
        const response = await axios.get(configData.LOCAL_HOST_URL + "/categories", axiosConfig)
        setCategoriesData(response.data)

        return response
    }

    let sendBannerData = {
        id: editObjectData.id,
        name: editObjectData.name.toString(),
        price: editObjectData.price.toString(),
        category: editObjectData.category,
        text: editObjectData.text.toString()
    }

    let sendCategoryData = {
        id: editObjectData.id,
        name: editObjectData.name.toString(),
        requestId: editObjectData.requestId.toString()
    }

    const fixIdToStr = () => {
        const valId = editObjectData.id
        const strId = valId.toString()

        sendBannerData = {...sendBannerData, id: strId}
    }

    async function sendBanner() {
        fixIdToStr()

        const response = await axios.post(configData.LOCAL_HOST_URL + "/add_banner", sendBannerData, axiosConfig)

        clearInputData()
        await updateBanners()

        return response
    }

    async function sendCategory() {
        const response = await axios.post(configData.LOCAL_HOST_URL + "/add_category", sendCategoryData, axiosConfig)

        clearInputData()
        await updateCategories()

        return response
    }

    async function markBannerAsDeleted() {
        clearInputData()

        const response = await axios.post(configData.LOCAL_HOST_URL + "/delete_banner", {deleteId: editObjectData.id}, axiosConfig)
        await updateBanners()

        return response
    }

    async function markCategoryAsDeleted() {
        clearInputData()

        const response = await axios.post(configData.LOCAL_HOST_URL + "/delete_category", {deleteId: editObjectData.id}, axiosConfig)
        await updateCategories()

        return response
    }

    const clearInputData = () => {
        setEditObjectData({id: '0', name: '', price: '', category: [], text: '', requestId: ''})
        props.setCurCatNumber(0)
    }


    return (
        <div className="ContentContainer">

            {props.currentMode === '1'
                ? <SearchPanel type={{name: 'Banner', plural: 'Banners'}}
                               setEditObject={setEditObjectData}
                               getBanners={bannersData}
                               currentMode={props.currentMode}
                               updateBannerFunc={updateBanners}
                               clearInputFunc={clearInputData}
                               updateCategoriesFunc={updateCategories}
                               updateCurCatCounter={props.setCurCatNumber}
                />

                : <SearchPanel type={{name: 'Category', plural: 'Categories'}}
                               setEditObject={setEditObjectData}
                               getCategories={categoriesData}
                               currentMode={props.currentMode}
                               clearInputFunc={clearInputData}
                               updateCategoriesFunc={updateCategories}
                               updateCurCatCounter={props.setCurCatNumber}
                />
            }
            {props.currentMode === '1'
                ? <EditPanelBanner label="Create new banner"
                                   dataVal={editObjectData}
                                   updateDataFunc={setEditObjectData}
                                   getCategories={categoriesData}
                                   updateBannerFunction={updateBanners}
                                   clearInputFunc={clearInputData}
                                   sendBannerFunc={sendBanner}
                                   deleteBannerFunc={markBannerAsDeleted}
                                   curCats={props.getCurCatNumber}
                                   setCurCats={props.setCurCatNumber}
                />

                : <EditPanelCategory label="ID:"
                                     dataVal={editObjectData}
                                     updateDataFunc={setEditObjectData}
                                     sendCategoryFunc={sendCategory}
                                     deleteCategoryFunc={markCategoryAsDeleted}
                                     curCats={props.getCurCatNumber}
                                     setCurCats={props.setCurCatNumber}
                />
            }

        </div>
    );
};

export default ContentContainer;