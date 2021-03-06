local Heart = class(Heart, function (  )
	-- body
	return display.newSprite("image/heart.png")
end)

local MATERIAL_DEFAULT = cc.PhysicsMaterial(0.0, 0.0, 0.0)

function Heart:ctor(x, y)
	-- body
	local heartBody = cc.PhysicsBody:createCircle(self:getContentSize().width / 2,
		MATERIAL_DEFAULT)

	heartBody:setCategoryBitmask(0x0001)
	heartBody:setContactTestBitmask(0x0100)
	heartBody:setCollisionBitmask(0x0001)
	
	self:setTag(HEART_TAG)

	self:setPhysicsBody(heartBody)
	self:getPhysicsBody():setGravityEnable(false)

	self:setPosition(x, y)

end

return Heart