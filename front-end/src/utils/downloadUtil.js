import JSZip from 'jszip'
import { saveAs } from 'file-saver'
import { doMethodByDownload } from './requestUtil'

export function download(url) {
    const alink = document.createElement("a");
    alink.href = url;
    alink.click();
}

export function batchDownloadToZip(urls, method, afterDownloadFuncation) {
    const jszip = new JSZip()
    let result = []
    urls.forEach((value, index) => {
        let promise = doMethodByDownload(value, method ? "GET" : method).then((res) => {
            const filename = decodeURIComponent(
                res.headers.get("content-disposition").split("filename=")[1]
            );
            jszip.file(filename, res.blob(), { binary: true })
        })
        result.push(promise)
    })
    Promise.all(result).then(() => {
        jszip.generateAsync({ type: "blob" }).then((res) => {
            saveAs(res, `批量下载${urls.length}个文件.zip`)
            if (typeof afterDownloadFuncation === 'function') {
                afterDownloadFuncation()
            }
        })
    })

}