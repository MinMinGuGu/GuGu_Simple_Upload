export function getQueryVariable(variable) {
    let location = window.location.toString()
    let query = location.substring(location.lastIndexOf('?') + 1);
    let vars = query.split("&");
    for (let i = 0; i < vars.length; i++) {
        let pair = vars[i].split("=");
        if (pair[0] === variable) { return pair[1]; }
    }
    return false;
}

export function getRoute() {
    let location = window.location.toString()
    const index = location.lastIndexOf('#')
    return location.substring(index + 1)
}