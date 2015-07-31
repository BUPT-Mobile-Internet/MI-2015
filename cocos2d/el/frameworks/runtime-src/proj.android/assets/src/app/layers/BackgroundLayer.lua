--意思是：这里出现的Heart都是以下路径中的Heart
local Heart = require("app.objects.Heart")
local Airship = require("app.objects.Airship")
local Bird = require("app.objects.Bird")

BackgroundLayer = class(BackgroundLayer, function ()
	-- body
	return display.newLayer()
end)

function BackgroundLayer:ctor(  )
	-- body
	self.distanceBg = { }
	self.nearbyBg = { }
	self.tiledMapBg = { }
	self.bird = { }

	self:createBackgrounds()

	local width = self.map:getContentSize().width
	local height1 = self.map:getContentSize().height * 9 / 10
    local height2 = self.map:getContentSize().height * 3 / 16

    --做一条天空一条地面的边界线
    local sky = display.newNode()
    local bodyTop = cc.PhysicsBody:createEdgeSegment(cc.p(0, height1), cc.p(width, height1), cc.PhysicsMaterial(0.0, 0.0, 0.0))
    
    bodyTop:setCategoryBitmask(0x1000)
    bodyTop:setContactTestBitmask(0x0000)
    bodyTop:setCollisionBitmask(0x0001)
    
    sky:setPhysicsBody(bodyTop)
    self:addChild(sky)

    local ground = display.newNode()
    local bodyBottom = cc.PhysicsBody:createEdgeSegment(cc.p(0, height2), cc.p(width, height2), cc.PhysicsMaterial(0.0, 0.0, 0.0))
    bodyBottom:setCategoryBitmask(0x1000)
    bodyBottom:setContactTestBitmask(0x0001)
    bodyBottom:setCollisionBitmask(0x0011)
 
    ground:setTag(GROUND_TAG)
    ground:setPhysicsBody(bodyBottom)
    self:addChild(ground)



	self:addBody("heart",Heart)
	self:addBody("airship",Airship)
	self:addBody("bird", Bird)




end


function BackgroundLayer:startGame( )
	-- body
	self:addNodeEventListener(cc.NODE_ENTER_FRAME_EVENT, handler(self, self.scrollBackgrounds))
	self:scheduleUpdate()
end



function BackgroundLayer:createBackgrounds(  )
	-- body
	--远景的显示
	local bg = display.newSprite("image/bj1.jpg")
	:pos(display.cx, display.cy)
	:addTo(self, -4)
	--稍远景的显示,bg1和bg2拼成左移的背景
	local bg1 = display.newSprite("image/b2.png")
	:align(display.BOTTOM_LEFT, display.left, display.bottom + 10)
	:addTo(self, -3)
	local bg2 = display.newSprite("image/b2.png")
	:align(display.BOTTOM_LEFT, bg1:getContentSize().width + display.left, display.bottom + 10)
	:addTo(self, -3)

	table.insert(self.distanceBg, bg1)
	table.insert(self.distanceBg, bg2)

	local emitter = cc.ParticleSystemQuad:create("particles/dirt.plist")
	emitter:setPosition(display.cx, display.top)
	self:addChild(emitter,-3)


	--近景的显示,bg1和bg2拼成左移的背景
	local bgn1 = display.newSprite("image/b1.png")
	:align(display.BOTTOM_LEFT, display.left, display.bottom)
	:addTo(self, -2)
	local bgn2 = display.newSprite("image/b1.png")
	:align(display.BOTTOM_LEFT, bgn1:getContentSize().width + display.left, display.bottom)
	:addTo(self, -2)

	table.insert(self.nearbyBg, bgn1)
	table.insert(self.nearbyBg, bgn2)


	--背景地图
	self.map = cc.TMXTiledMap:create("image/map.tmx")
	:align(display.BOTTOM_LEFT, display.left, display.bottom)
	:addTo(self, -1)
	table.insert(self.tiledMapBg, self.map)





end


function BackgroundLayer:scrollBackgrounds(dt)
	-- body

    if self.distanceBg[2]:getPositionX() <= 0 then
        self.distanceBg[1]:setPositionX(0)
    end
 	--[[用bg1替换bg2的位置，bg2放到bg1的后面，然后继续循环
 	]]

    local x1 = self.distanceBg[1]:getPositionX() - 50*dt -- 50 相当于速度
    local x2 = x1 + self.distanceBg[1]:getContentSize().width 
 
    self.distanceBg[1]:setPositionX(x1)
    self.distanceBg[2]:setPositionX(x2)




    if self.nearbyBg[2]:getPositionX() <= 0 then
        self.nearbyBg[1]:setPositionX(0)
    end
 	--[[用bgn1替换bgn2的位置，bgn2放到bgn1的后面，然后继续循环
 	]]

    local x3 = self.nearbyBg[1]:getPositionX() - 130*dt -- 130*dt 相当于速度
    local x4 = x3 + self.nearbyBg[1]:getContentSize().width 
 
    self.nearbyBg[1]:setPositionX(x3)
    self.nearbyBg[2]:setPositionX(x4)

    --背景滚动
    if self.map:getPositionX() <= display.width - self.map:getContentSize().width then
    	 self:unscheduleUpdate()  -- 禁用帧事件，停止整个背景层滚动
    end

    local x5 = self.map:getPositionX() - 130*dt
    self.map:setPositionX(x5)

    self:addVelocityToBird()

end

function BackgroundLayer:addBody(objectGroupName,class)
	-- body
	local objects = self.map:getObjectGroup(objectGroupName):getObjects()

	local  dict   =    nil
	local  i      =    0
	local  len    =    table.getn(objects)	

	for i = 0 ,len - 1, 1 do
		dict = objects[i + 1]
		if dict == nil then
			break
		end
		local key = "x"
		local x   = dict["x"]
		local key = "y"
		local y   = dict["y"]
		local sprite = class.new(x,y)
		self.map:addChild(sprite)

		if class == Bird then
			table.insert(self.bird,sprite)
		end

	end
end

function BackgroundLayer:addVelocityToBird()
	-- body
	local  dict  =   nil
	local  i     =   0
	local  len   =   table.getn(self.bird)

	for i = 0 ,len - 1, 1 do
		dict = self.bird[i + 1]
		if dict == nil then
			break
		end

		local x = dict:getPositionX()
		if x <= display.width - self.map:getPositionX() then
			if dict:getPhysicsBody():getVelocity().x == 0 then
				dict:getPhysicsBody():setVelocity(cc.p(-70,math.random(-40,40)))
			else
				table.remove(self.bird,i + 1)
			end
		end

	end
end


return BackgroundLayer