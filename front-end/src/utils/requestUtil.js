export const requestErrorCode = 499

function cleanArray(actual) {
    const newArray = []
    for (let i = 0; i < actual.length; i++) {
        if (actual[i]) {
            newArray.push(actual[i])
        }
    }
    return newArray
}

function handlerParams(json) {
    if (!json) return ''
    return cleanArray(Object.keys(json).map(key => {
        if (json[key] === undefined) return ''
        return encodeURIComponent(key) + '=' +
            encodeURIComponent(json[key])
    })).join('&')
}

function generateUrl(uri, params) {
    const paramsQueryStr = handlerParams(params)
    return uri + paramsQueryStr
}

function generateError(msg) {
    return { "code": requestErrorCode, msg }
}

export async function doGet(uri, params, header) {
    try {
        const requestUrl = generateUrl(uri, params)
        const response = await fetch(requestUrl, {
            method: "GET",
            headers: JSON.stringify(header)
        })
        return await response.json()
    } catch (error) {
        return generateError('发起请求失败')
    }
}

export async function doPost(uri, params, header) {
    const requestUrl = generateUrl(uri)
    try {
        const response = await fetch(requestUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                headers: JSON.stringify(header)
            },
            body: JSON.stringify(params)
        })
        return await response.json()
    } catch (error) {
        return generateError('发起请求失败')
    }
}

export async function doMethod(uri, method, params, header) {
    const requestUrl = generateUrl(uri)
    try {
        const response = await fetch(requestUrl, {
            method: method,
            headers: {
                "Content-Type": "application/json",
                headers: JSON.stringify(header)
            },
            body: JSON.stringify(params)
        })
        return await response.json()
    } catch (error) {
        return generateError('发起请求失败')
    }
}