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
    if (json) {
        return "?" + cleanArray(Object.keys(json).map(key => {
            if (json[key]) {
                return encodeURIComponent(key) + '=' +
                    encodeURIComponent(json[key])
            }
            return ''
        })).join('&')
    }
    return '';
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
            headers: header || {}
        })
        // todo 优化如果请求登录失效
        return await response.json()
    } catch (error) {
        console.error(error)
        return generateError('发起请求失败')
    }
}

export async function doGetResultful(uri, header, ...params) {
    let url = uri;
    for (const key in params) {
        if (Object.hasOwnProperty.call(params, key)) {
            const element = params[key];
            url += `/${element}`;
        }
    }
    url = url.replace('//', '');
    return doGet(url, null, header)
}

export async function doPost(uri, params, header) {
    return dataInBody("POST", uri, params, header)
}

export async function doPut(uri, params, header) {
    return dataInBody("PUT", uri, params, header)
}

export async function doDel(uri, params, header) {
    return dataInBody("DELETE", uri, params, header)
}

async function dataInBody(method, uri, params, header) {
    const requestUrl = generateUrl(uri)
    const requestParams = params || {};
    try {
        const response = await fetch(requestUrl, {
            method,
            headers: {
                "Content-Type": "application/json",
                headers: header || {}
            },
            body: JSON.stringify(requestParams)
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

export async function doMethodByDownload(uri, method, params, header) {
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
        return response
    } catch (error) {
        return generateError('发起请求失败')
    }
}