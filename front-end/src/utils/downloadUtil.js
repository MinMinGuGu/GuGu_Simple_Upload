export function creteALinkDownload(response, afterDownloadFuncation) {
    // 必须为fetch对象
    response.then((res) => {
        const filename = decodeURIComponent(
            res.headers.get("content-disposition").split("filename=")[1]
        );
        res.blob().then((blob) => {
            const alink = document.createElement("a");
            alink.style.display = "none";
            alink.href = window.URL.createObjectURL(blob);
            alink.download = filename;
            document.body.appendChild(alink);
            alink.click();
            URL.revokeObjectURL(alink.href);
            document.body.removeChild(alink);
            afterDownloadFuncation()
        });
    });
}