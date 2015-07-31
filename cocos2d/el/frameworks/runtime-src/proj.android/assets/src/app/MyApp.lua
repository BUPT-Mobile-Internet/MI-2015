
require("config")
require("cocos.init")
require("framework.init")

local MyApp = class("MyApp", cc.mvc.AppBase)

function MyApp:ctor()
    MyApp.super.ctor(self)
end

function MyApp:run()
    cc.FileUtils:getInstance():addSearchPath("res/")
    cc.Director:getInstance():setContentScaleFactor(640/CONFIG_SCREEN_HEIGHT)
    display.addSpriteFrames("image/player.plist", "image/player.pvr.ccz")
    self:enterScene("MainScene")
    math.randomseed(os.time())
end
--此处加载所需的文件资源
require("app.layers.BackgroundLayer")
require("app.objects.Player")
require("app.objects.Heart")
require("app.objects.Airship")
require("app.objects.Bird")

audio.preloadMusic("sound/background.mp3") 
audio.preloadSound("sound/button.wav")
audio.preloadSound("sound/ground.mp3")
audio.preloadSound("sound/heart.mp3")
audio.preloadSound("sound/hit.mp3")

return MyApp
