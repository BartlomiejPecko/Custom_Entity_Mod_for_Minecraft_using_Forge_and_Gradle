package net.aqueox.project.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.scores.Team;
import net.minecraftforge.event.ForgeEventFactory;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class NoxEntity extends TamableAnimal implements IAnimatable {

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    private static final EntityDataAccessor<Boolean> SITTING =
            SynchedEntityData.defineId(NoxEntity.class, EntityDataSerializers.BOOLEAN);

    public NoxEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier setAttributes(){
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 180.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0f)
                .add(Attributes.ATTACK_SPEED, 1.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f).build();}

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.nox.idle", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }

        if (this.isSitting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.nox.sit", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.nox.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    @Override
    public void registerControllers(AnimationData data) {

        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));

    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(1, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));

        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Creeper.class, true));
    }
    public boolean causeFallDamage(float p_149683_, float p_149684_, DamageSource p_149685_) {
        return false;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLAZE_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.BLAZE_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.BLAZE_DEATH;
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    /* TAMEABLE */
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();

        Item itemForTaming = Items.SNOWBALL;

        if (item == itemForTaming && !isTame()) {
            if (this.level.isClientSide) {
                return InteractionResult.CONSUME;
            } else {
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                if (!ForgeEventFactory.onAnimalTame(this, player)) {
                    if (!this.level.isClientSide) {
                        super.tame(player);
                        this.navigation.recomputePath();
                        this.setTarget(null);
                        this.level.broadcastEntityEvent(this, (byte)7);
                        setSitting(true);
                    }
                }

                return InteractionResult.SUCCESS;
            }
        }

        if(isTame() && !this.level.isClientSide && hand == InteractionHand.MAIN_HAND) {
            setSitting(!isSitting());
            return InteractionResult.SUCCESS;
        }

        if (itemstack.getItem() == itemForTaming) {
            return InteractionResult.PASS;
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setSitting(tag.getBoolean("isSitting"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("isSitting", this.isSitting());
    }

    @Override
    protected void defineSynchedData(){
        super.defineSynchedData();
        this.entityData.define(SITTING, false);
    }

    public void setSitting(boolean sitting) {
        this.entityData.set(SITTING, sitting);
        this.setOrderedToSit(sitting);
    }

    public boolean isSitting(){
        return this.entityData.get(SITTING);
    }

    @Override
    public Team getTeam(){
        return super.getTeam();
    }

    public boolean canBeLeashed(Player player){
        return false;
    }

    @Override
    public void setTame(boolean tamed) {
        super.setTame(tamed);
        if (tamed) {
            getAttribute(Attributes.MAX_HEALTH).setBaseValue(195.0D);
            getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(6D);
            getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double) 0.55f);
        } else {
            getAttribute(Attributes.MAX_HEALTH).setBaseValue(130.0D);
            getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3D);
            getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double) 0.42f);
        }
    }

}
