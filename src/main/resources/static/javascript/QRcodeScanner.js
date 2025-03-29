const video = document.querySelector("#scanner");
const result = document.querySelector("#result");
const allowedURL = "http:/localhost:8080";
navigator.mediaDevices.getUserMedia({ video: { facingMode: "environment" } })
    .then(function (stream) {
        video.srcObject = stream;
        const track = stream.getVideoTracks()[0];
        const capabilities = track.getCapabilities();

        if (capabilities.focusMode) {
            track.applyConstraints({ advanced: [{ focusMode: "continuous" }] });
        }

        scanQRCode();
    })
    .catch(function (err) {
        console.error("Det har oppstått en feil: " + err);
    });

function scanQRCode() {
    const canvas = document.createElement("canvas");
    const context = canvas.getContext("2d");

    function processFrame() {
        if (video.readyState === video.HAVE_ENOUGH_DATA) {
            canvas.height = video.videoHeight;
            canvas.width = video.videoWidth;
            context.drawImage(video, 0, 0, canvas.width, canvas.height);

            const imageData = context.getImageData(0, 0, canvas.width, canvas.height);
            const code = jsQR(imageData.data, canvas.width, canvas.height, { inversionAttempts: "dontInvert" });

            if (code) {
                if (code.data.startsWith(allowedURL)){
                    alert("Det har oppstått en feil med QR-koden")
                } else{
                    window.location.href = code.data;
                }
            }
        }
        requestAnimationFrame(processFrame);
    }

    processFrame();
}