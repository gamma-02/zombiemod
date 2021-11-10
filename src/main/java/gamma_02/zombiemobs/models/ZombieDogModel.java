package gamma_02.zombiemobs.models;

import com.google.common.collect.ImmutableList;
import gamma_02.zombiemobs.entities.ZombieDog;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.TintableAnimalModel;
import net.minecraft.util.math.MathHelper;

public class ZombieDogModel extends TintableAnimalModel<ZombieDog>
{
    /**
     * The key of the real head model part, whose value is {@value}.
     */
    private static final String REAL_HEAD = "real_head";
    /**
     * The key of the upper body model part, whose value is {@value}.
     */
    private static final String UPPER_BODY = "upper_body";
    /**
     * The key of the real tail model part, whose value is {@value}.
     */
    private static final String REAL_TAIL = "real_tail";
    /**
     * The main bone used to animate the head. Contains {@link #realHead} as one of its children.
     */
    private final ModelPart head;
    private final ModelPart realHead;
    private final ModelPart torso;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    /**
     * The main bone used to animate the tail. Contains {@link #realTail} as one of its children.
     */
    private final ModelPart tail;
    private final ModelPart realTail;
    private final ModelPart neck;
    private static final int field_32580 = 8;

    public ZombieDogModel(ModelPart modelPart) {
        this.head = modelPart.getChild(EntityModelPartNames.HEAD);
        this.realHead = this.head.getChild("real_head");
        this.torso = modelPart.getChild(EntityModelPartNames.BODY);
        this.neck = modelPart.getChild("upper_body");
        this.rightHindLeg = modelPart.getChild(EntityModelPartNames.RIGHT_HIND_LEG);
        this.leftHindLeg = modelPart.getChild(EntityModelPartNames.LEFT_HIND_LEG);
        this.rightFrontLeg = modelPart.getChild(EntityModelPartNames.RIGHT_FRONT_LEG);
        this.leftFrontLeg = modelPart.getChild(EntityModelPartNames.LEFT_FRONT_LEG);
        this.tail = modelPart.getChild(EntityModelPartNames.TAIL);
        this.realTail = this.tail.getChild("real_tail");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        float f = 13.5F;
        ModelPartData modelPartData2 = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 13.5F, -7.0F));
        modelPartData2.addChild("real_head", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F).uv(16, 14).cuboid(-2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F).uv(16, 14).cuboid(2.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F).uv(0, 10).cuboid(-0.5F, 0.0F, -5.0F, 3.0F, 3.0F, 4.0F), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(18, 14).cuboid(-3.0F, -2.0F, -3.0F, 6.0F, 9.0F, 6.0F), ModelTransform.of(0.0F, 14.0F, 2.0F, 1.5707964F, 0.0F, 0.0F));
        modelPartData.addChild("upper_body", ModelPartBuilder.create().uv(21, 0).cuboid(-3.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F), ModelTransform.of(-1.0F, 14.0F, -3.0F, 1.5707964F, 0.0F, 0.0F));
        ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(0, 18).cuboid(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F);
        modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, modelPartBuilder, ModelTransform.pivot(-2.5F, 16.0F, 7.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, modelPartBuilder, ModelTransform.pivot(0.5F, 16.0F, 7.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, modelPartBuilder, ModelTransform.pivot(-2.5F, 16.0F, -4.0F));
        modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, modelPartBuilder, ModelTransform.pivot(0.5F, 16.0F, -4.0F));
        ModelPartData modelPartData3 = modelPartData.addChild(EntityModelPartNames.TAIL, ModelPartBuilder.create(), ModelTransform.of(-1.0F, 12.0F, 8.0F, 0.62831855F, 0.0F, 0.0F));
        modelPartData3.addChild("real_tail", ModelPartBuilder.create().uv(9, 18).cuboid(0.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 32);
    }

    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.torso, this.rightHindLeg, this.leftHindLeg, this.rightFrontLeg, this.leftFrontLeg, this.tail, this.neck);
    }

    public void animateModel(ZombieDog wolfEntity, float f, float g, float h) {
        if (wolfEntity.hasAngerTime()) {
            this.tail.yaw = 0.0F;
        } else {
            this.tail.yaw = MathHelper.cos(f * 0.6662F) * 1.4F * g;
        }

        if (wolfEntity.isInSittingPose()) {
            this.neck.setPivot(-1.0F, 16.0F, -3.0F);
            this.neck.pitch = 1.2566371F;
            this.neck.yaw = 0.0F;
            this.torso.setPivot(0.0F, 18.0F, 0.0F);
            this.torso.pitch = 0.7853982F;
            this.tail.setPivot(-1.0F, 21.0F, 6.0F);
            this.rightHindLeg.setPivot(-2.5F, 22.7F, 2.0F);
            this.rightHindLeg.pitch = 4.712389F;
            this.leftHindLeg.setPivot(0.5F, 22.7F, 2.0F);
            this.leftHindLeg.pitch = 4.712389F;
            this.rightFrontLeg.pitch = 5.811947F;
            this.rightFrontLeg.setPivot(-2.49F, 17.0F, -4.0F);
            this.leftFrontLeg.pitch = 5.811947F;
            this.leftFrontLeg.setPivot(0.51F, 17.0F, -4.0F);
        } else {
            this.torso.setPivot(0.0F, 14.0F, 2.0F);
            this.torso.pitch = 1.5707964F;
            this.neck.setPivot(-1.0F, 14.0F, -3.0F);
            this.neck.pitch = this.torso.pitch;
            this.tail.setPivot(-1.0F, 12.0F, 8.0F);
            this.rightHindLeg.setPivot(-2.5F, 16.0F, 7.0F);
            this.leftHindLeg.setPivot(0.5F, 16.0F, 7.0F);
            this.rightFrontLeg.setPivot(-2.5F, 16.0F, -4.0F);
            this.leftFrontLeg.setPivot(0.5F, 16.0F, -4.0F);
            this.rightHindLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g;
            this.leftHindLeg.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * g;
            this.rightFrontLeg.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * g;
            this.leftFrontLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g;
        }

        this.realHead.roll = wolfEntity.getBegAnimationProgress(h) + wolfEntity.getShakeAnimationProgress(h, 0.0F);
        this.neck.roll = wolfEntity.getShakeAnimationProgress(h, -0.08F);
        this.torso.roll = wolfEntity.getShakeAnimationProgress(h, -0.16F);
        this.realTail.roll = wolfEntity.getShakeAnimationProgress(h, -0.2F);
    }

    public void setAngles(ZombieDog wolfEntity, float f, float g, float h, float i, float j) {
        this.head.pitch = j * 0.017453292F;
        this.head.yaw = i * 0.017453292F;
        this.tail.pitch = h;
    }
}