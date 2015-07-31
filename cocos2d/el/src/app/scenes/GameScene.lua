local Player = require("app.objects.Player")

local GameScene = class("GameScene", function()

    return display.newPhysicsScene("GameScene")
end)
 
function GameScene:ctor()
	self.backgroundLayer = BackgroundLayer.new()
	self.backgroundLayer:addTo(self)
	--[[注意！如果直接使用
	:setLocalZOrder(-1)有风险，可能比的地方不认，最好是使用下面
	对象:函数(参数)的形式;
	--]]
	self.backgroundLayer:setLocalZOrder(-1)

	

	--添加玩家
    self.player = Player.new()
    self.player:flying()
    self.player:setPosition(-20, display.height * 2 / 3)
    self.player:setLocalZOrder(0)  
    self:addChild(self.player)
    self:playerFlyToScene()

    self.player:createProgress()

    


	self.world = self:getPhysicsWorld()
	self.world:setGravity(cc.p(0,-198.0))
	--self.world:setDebugDrawMask(cc.PhysicsWorld.DEBUGDRAW_ALL)

	self:addCollision()

end


function GameScene:playerFlyToScene()
 
    local function startDrop()
        self.player:getPhysicsBody():setGravityEnable(true)  
        self.player:drop() 
        self.backgroundLayer:startGame()

        --添加节点事件监听，第一个参数表明是触摸事件
        self.backgroundLayer:addNodeEventListener(cc.NODE_TOUCH_EVENT, function(event)
        	return self:onTouch(event.name, event.x, event.y)
        end)
        self.backgroundLayer:setTouchEnabled(true)
    end
 
    local animation = display.getAnimationCache("flying")
    transition.playAnimationForever(self.player, animation)
 
    local action = transition.sequence({
        cc.MoveTo:create(4, cc.p(display.cx, display.height * 2 / 3)),
        cc.CallFunc:create(startDrop)
    })
    self.player:runAction(action)
end


function GameScene:onTouch( event, x, y )
	if event == "began" then
		self.player:flying()
		self.player:getPhysicsBody():setVelocity(cc.p(0,98))
		return true
	elseif event == "moved" then
			
	elseif event == "ended" then
		self.player:drop()
	end
end


function GameScene:addCollision(  )
	local function contactLogic( node )
        if node:getTag() == HEART_TAG then
            --给玩家增加血量，并添加心心消除特效，下章会加上
            local emitter = cc.ParticleSystemQuad:create("particles/stars.plist")
            emitter:setBlendAdditive(false)
            emitter:setPosition(node:getPosition())
            self.backgroundLayer.map:addChild(emitter)
            if self.player.blood <= 100 then
        		--todo
        		self.player.blood = self.player.blood + 20
        		self.player:setProPercentage(self.player.blood)
        	end

            node:removeFromParent()
        -- 5
        elseif node:getTag() == GROUND_TAG then
            -- 减少玩家20点血量，并添加玩家受伤动画，下章会加上
             if self.player.blood > 0 then
        		--todo
        		self.player:hit()
        		self.player.blood = self.player.blood - 20
        		self.player:setProPercentage(self.player.blood)
        	end           
        elseif node:getTag() == AIRSHIP_TAG then
            -- 减少玩家10点血量，并添加玩家受伤动画
             if self.player.blood > 0 then
        		--todo
        		self.player:hit()
        		self.player.blood = self.player.blood - 10
        		self.player:setProPercentage(self.player.blood)
        	end     

        elseif node:getTag() == BIRD_TAG then
            -- 减少玩家5点血量，并添加玩家受伤动画
             if self.player.blood > 0 then
        		--todo
        		self.player:hit()
        		self.player.blood = self.player.blood - 5
        		self.player:setProPercentage(self.player.blood)
        	end     
		end
	end


	local function onContactBegin( contact )
		local a = contact:getShapeA():getBody():getNode()
		local b = contact:getShapeB():getBody():getNode()
		contactLogic(a)
		contactLogic(b)
		return true
	end

	local function onContactSeperate(contact )
		if self.player.blood <= 0 then
			self.backgroundLayer:unscheduleUpdate()
			self.player:die()

			local over = display.newSprite("image/over.png")
			over:setPosition(display.cx, display.cy)
			self:addChild(over)

			cc.Director:getInstance():getEventDispatcher():removeAllEventListeners()
		end
		
	end

	local contactListener = cc.EventListenerPhysicsContact:create()
	contactListener:registerScriptHandler(onContactBegin,cc.Handler.EVENT_PHYSICS_CONTACT_BEGIN)
	contactListener:registerScriptHandler(onContactSeperate,cc.Handler.EVENT_PHYSICS_CONTACT_SEPERATE)
	local eventDispatcher = cc.Director:getInstance():getEventDispatcher()
	eventDispatcher:addEventListenerWithFixedPriority(contactListener, 1)

end



function GameScene:onEnter()
end
 
function GameScene:onExit()
end
 
return GameScene