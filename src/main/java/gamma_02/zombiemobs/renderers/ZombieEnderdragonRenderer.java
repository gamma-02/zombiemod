package gamma_02.zombiemobs.renderers;

import gamma_02.zombiemobs.RenderInit;
import gamma_02.zombiemobs.entities.ZombieEnderDragon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EndCrystalEntityRenderer;
import net.minecraft.client.render.entity.EnderDragonEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static gamma_02.zombiemobs.ZombieMod.ModID;

public class ZombieEnderdragonRenderer extends EntityRenderer<ZombieEnderDragon>
{
    public static final Identifier CRYSTAL_BEAM_TEXTURE = new Identifier("textures/entity/end_crystal/end_crystal_beam.png");
    private static final Identifier EXPLOSION_TEXTURE = new Identifier(ModID, "textures/allt-exploding.png");
    private static final Identifier TEXTURE = new Identifier(ModID, "textures/enderdragon_04.png");
    private static final Identifier EYE_TEXTURE = new Identifier(ModID,"textures/enderdragon_eyes.png");
    private static final RenderLayer DRAGON_CUTOUT;
    private static final RenderLayer DRAGON_DECAL;
    private static final RenderLayer DRAGON_EYES;
    private static final RenderLayer CRYSTAL_BEAM_LAYER;
    private static final float HALF_SQRT_3;
    private final ZombieEnderdragonRenderer.DragonEntityModel model;

    public ZombieEnderdragonRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
        this.model = new ZombieEnderdragonRenderer.DragonEntityModel(context.getPart(RenderInit.DRAGON_LAYER));
    }

    public void render(ZombieEnderDragon enderDragonEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        float h = (float)enderDragonEntity.getSegmentProperties(7, g)[0];
        float j = (float)(enderDragonEntity.getSegmentProperties(5, g)[1] - enderDragonEntity.getSegmentProperties(10, g)[1]);
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-h));
        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(j * 10.0F));
        matrixStack.translate(0.0D, 0.0D, 1.0D);
        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        matrixStack.translate(0.0D, -1.5010000467300415D, 0.0D);
        boolean bl = enderDragonEntity.hurtTime > 0;
        this.model.animateModel(enderDragonEntity, 0.0F, 0.0F, g);
        VertexConsumer vertexConsumer1;
        if (enderDragonEntity.ticksSinceDeath > 0) {
            float k = (float)enderDragonEntity.ticksSinceDeath / 200.0F;
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityAlpha(EXPLOSION_TEXTURE));
            this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, k);
            VertexConsumer vertexConsumer2 = vertexConsumerProvider.getBuffer(DRAGON_DECAL);
            this.model.render(matrixStack, vertexConsumer2, i, OverlayTexture.getUv(0.0F, bl), 1.0F, 1.0F, 1.0F, 1.0F);
        } else {
            vertexConsumer1 = vertexConsumerProvider.getBuffer(DRAGON_CUTOUT);
            this.model.render(matrixStack, vertexConsumer1, i, OverlayTexture.getUv(0.0F, bl), 1.0F, 1.0F, 1.0F, 1.0F);
        }

        vertexConsumer1 = vertexConsumerProvider.getBuffer(DRAGON_EYES);
        this.model.render(matrixStack, vertexConsumer1, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        float vertexConsumer;
        float vertexConsumer2;
        if (enderDragonEntity.ticksSinceDeath > 0) {
            vertexConsumer = ((float)enderDragonEntity.ticksSinceDeath + g) / 200.0F;
            vertexConsumer2 = Math.min(vertexConsumer > 0.8F ? (vertexConsumer - 0.8F) / 0.2F : 0.0F, 1.0F);
            Random random = new Random(432L);
            VertexConsumer vertexConsumer3 = vertexConsumerProvider.getBuffer(RenderLayer.getLightning());
            matrixStack.push();
            matrixStack.translate(0.0D, -1.0D, -2.0D);

            for(int l = 0; (float)l < (vertexConsumer + vertexConsumer * vertexConsumer) / 2.0F * 60.0F; ++l) {
                matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(random.nextFloat() * 360.0F));
                matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(random.nextFloat() * 360.0F));
                matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(random.nextFloat() * 360.0F));
                matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(random.nextFloat() * 360.0F));
                matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(random.nextFloat() * 360.0F));
                matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(random.nextFloat() * 360.0F + vertexConsumer * 90.0F));
                float m = random.nextFloat() * 20.0F + 5.0F + vertexConsumer2 * 10.0F;
                float n = random.nextFloat() * 2.0F + 1.0F + vertexConsumer2 * 2.0F;
                Matrix4f matrix4f = matrixStack.peek().getModel();
                int o = (int)(255.0F * (1.0F - vertexConsumer2));
                method_23157(vertexConsumer3, matrix4f, o);
                method_23156(vertexConsumer3, matrix4f, m, n);
                method_23158(vertexConsumer3, matrix4f, m, n);
                method_23157(vertexConsumer3, matrix4f, o);
                method_23158(vertexConsumer3, matrix4f, m, n);
                method_23159(vertexConsumer3, matrix4f, m, n);
                method_23157(vertexConsumer3, matrix4f, o);
                method_23159(vertexConsumer3, matrix4f, m, n);
                method_23156(vertexConsumer3, matrix4f, m, n);
            }

            matrixStack.pop();
        }

        matrixStack.pop();
        if (enderDragonEntity.connectedCrystal != null) {
            matrixStack.push();
            vertexConsumer = (float)(enderDragonEntity.connectedCrystal.getX() - MathHelper.lerp((double)g, enderDragonEntity.prevX, enderDragonEntity.getX()));
            vertexConsumer2 = (float)(enderDragonEntity.connectedCrystal.getY() - MathHelper.lerp((double)g, enderDragonEntity.prevY, enderDragonEntity.getY()));
            float random = (float)(enderDragonEntity.connectedCrystal.getZ() - MathHelper.lerp((double)g, enderDragonEntity.prevZ, enderDragonEntity.getZ()));
            renderCrystalBeam(vertexConsumer, vertexConsumer2 + EndCrystalEntityRenderer.getYOffset(enderDragonEntity.connectedCrystal, g), random, g, enderDragonEntity.age, matrixStack, vertexConsumerProvider, i);
            matrixStack.pop();
        }

        super.render(enderDragonEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    private static void method_23157(VertexConsumer vertices, Matrix4f matrix, int alpha) {
        vertices.vertex(matrix, 0.0F, 0.0F, 0.0F).color(255, 255, 255, alpha).next();
    }

    private static void method_23156(VertexConsumer vertices, Matrix4f matrix, float y, float x) {
        vertices.vertex(matrix, -HALF_SQRT_3 * x, y, -0.5F * x).color(255, 0, 255, 0).next();
    }

    private static void method_23158(VertexConsumer vertices, Matrix4f matrix, float y, float x) {
        vertices.vertex(matrix, HALF_SQRT_3 * x, y, -0.5F * x).color(255, 0, 255, 0).next();
    }

    private static void method_23159(VertexConsumer vertices, Matrix4f matrix, float y, float z) {
        vertices.vertex(matrix, 0.0F, y, 1.0F * z).color(255, 0, 255, 0).next();
    }

    public static void renderCrystalBeam(float dx, float dy, float dz, float tickDelta, int age, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        float f = MathHelper.sqrt(dx * dx + dz * dz);
        float g = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
        matrices.push();
        matrices.translate(0.0D, 2.0D, 0.0D);
        matrices.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion((float)(-Math.atan2((double)dz, (double)dx)) - 1.5707964F));
        matrices.multiply(Vec3f.POSITIVE_X.getRadialQuaternion((float)(-Math.atan2((double)f, (double)dy)) - 1.5707964F));
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(CRYSTAL_BEAM_LAYER);
        float h = 0.0F - ((float)age + tickDelta) * 0.01F;
        float i = MathHelper.sqrt(dx * dx + dy * dy + dz * dz) / 32.0F - ((float)age + tickDelta) * 0.01F;

        float k = 0.0F;
        float l = 0.75F;
        float m = 0.0F;
        MatrixStack.Entry entry = matrices.peek();
        Matrix4f matrix4f = entry.getModel();
        Matrix3f matrix3f = entry.getNormal();

        for(int n = 1; n <= 8; ++n) {
            float o = MathHelper.sin((float)n * 6.2831855F / 8.0F) * 0.75F;
            float p = MathHelper.cos((float)n * 6.2831855F / 8.0F) * 0.75F;
            float q = (float)n / 8.0F;
            vertexConsumer.vertex(matrix4f, k * 0.2F, l * 0.2F, 0.0F).color(0, 0, 0, 255).texture(m, h).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
            vertexConsumer.vertex(matrix4f, k, l, g).color(255, 255, 255, 255).texture(m, i).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
            vertexConsumer.vertex(matrix4f, o, p, g).color(255, 255, 255, 255).texture(q, i).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
            vertexConsumer.vertex(matrix4f, o * 0.2F, p * 0.2F, 0.0F).color(0, 0, 0, 255).texture(q, h).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
            k = o;
            l = p;
            m = q;
        }

        matrices.pop();
    }

    public Identifier getTexture(ZombieEnderDragon enderDragonEntity) {
        return TEXTURE;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        float f = -16.0F;
        ModelPartData modelPartData2 = modelPartData.addChild(
                EntityModelPartNames.HEAD, ModelPartBuilder.create().cuboid("upperlip", -6.0F, -1.0F, -24.0F, 12, 5, 16, 176, 44).cuboid("upperhead", -8.0F, -8.0F, -10.0F, 16, 16, 16, 112, 30).mirrored().cuboid("scale", -5.0F, -12.0F, -4.0F, 2, 4, 6, 0, 0).cuboid("nostril", -5.0F, -3.0F, -22.0F, 2, 2, 4, 112, 0).mirrored().cuboid("scale", 3.0F, -12.0F, -4.0F, 2, 4, 6, 0, 0).cuboid("nostril", 3.0F, -3.0F, -22.0F, 2, 2, 4, 112, 0), ModelTransform.NONE);
        modelPartData2.addChild(EntityModelPartNames.JAW, ModelPartBuilder.create().cuboid(EntityModelPartNames.JAW, -6.0F, 0.0F, -16.0F, 12, 4, 16, 176, 65), ModelTransform.pivot(0.0F, 4.0F, -8.0F));
        modelPartData.addChild(EntityModelPartNames.NECK, ModelPartBuilder.create().cuboid("box", -5.0F, -5.0F, -5.0F, 10, 10, 10, 192, 104).cuboid("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6, 48, 0), ModelTransform.NONE);
        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().cuboid(EntityModelPartNames.BODY, -12.0F, 0.0F, -16.0F, 24, 24, 64, 0, 0).cuboid("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12, 220, 53).cuboid("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12, 220, 53).cuboid("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12, 220, 53), ModelTransform.pivot(0.0F, 4.0F, 8.0F));
        ModelPartData modelPartData3 = modelPartData.addChild(EntityModelPartNames.LEFT_WING, ModelPartBuilder.create().mirrored().cuboid("bone", 0.0F, -4.0F, -4.0F, 56, 8, 8, 112, 88).cuboid("skin", 0.0F, 0.0F, 2.0F, 56, 0, 56, -56, 88), ModelTransform.pivot(12.0F, 5.0F, 2.0F));
        modelPartData3.addChild(EntityModelPartNames.LEFT_WING_TIP, ModelPartBuilder.create().mirrored().cuboid("bone", 0.0F, -2.0F, -2.0F, 56, 4, 4, 112, 136).cuboid("skin", 0.0F, 0.0F, 2.0F, 56, 0, 56, -56, 144), ModelTransform.pivot(56.0F, 0.0F, 0.0F));
        ModelPartData modelPartData4 = modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, ModelPartBuilder.create().cuboid("main", -4.0F, -4.0F, -4.0F, 8, 24, 8, 112, 104), ModelTransform.pivot(12.0F, 20.0F, 2.0F));
        ModelPartData modelPartData5 = modelPartData4.addChild(EntityModelPartNames.LEFT_FRONT_LEG_TIP, ModelPartBuilder.create().cuboid("main", -3.0F, -1.0F, -3.0F, 6, 24, 6, 226, 138), ModelTransform.pivot(0.0F, 20.0F, -1.0F));
        modelPartData5.addChild(EntityModelPartNames.LEFT_FRONT_FOOT, ModelPartBuilder.create().cuboid("main", -4.0F, 0.0F, -12.0F, 8, 4, 16, 144, 104), ModelTransform.pivot(0.0F, 23.0F, 0.0F));
        ModelPartData modelPartData6 = modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, ModelPartBuilder.create().cuboid("main", -8.0F, -4.0F, -8.0F, 16, 32, 16, 0, 0), ModelTransform.pivot(16.0F, 16.0F, 42.0F));
        ModelPartData modelPartData7 = modelPartData6.addChild(EntityModelPartNames.LEFT_HIND_LEG_TIP, ModelPartBuilder.create().cuboid("main", -6.0F, -2.0F, 0.0F, 12, 32, 12, 196, 0), ModelTransform.pivot(0.0F, 32.0F, -4.0F));
        modelPartData7.addChild(EntityModelPartNames.LEFT_HIND_FOOT, ModelPartBuilder.create().cuboid("main", -9.0F, 0.0F, -20.0F, 18, 6, 24, 112, 0), ModelTransform.pivot(0.0F, 31.0F, 4.0F));
        ModelPartData modelPartData8 = modelPartData.addChild(EntityModelPartNames.RIGHT_WING, ModelPartBuilder.create().cuboid("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8, 112, 88).cuboid("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56, -56, 88), ModelTransform.pivot(-12.0F, 5.0F, 2.0F));
        modelPartData8.addChild(EntityModelPartNames.RIGHT_WING_TIP, ModelPartBuilder.create().cuboid("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4, 112, 136).cuboid("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56, -56, 144), ModelTransform.pivot(-56.0F, 0.0F, 0.0F));
        ModelPartData modelPartData9 = modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, ModelPartBuilder.create().cuboid("main", -4.0F, -4.0F, -4.0F, 8, 24, 8, 112, 104), ModelTransform.pivot(-12.0F, 20.0F, 2.0F));
        ModelPartData modelPartData10 = modelPartData9.addChild(EntityModelPartNames.RIGHT_FRONT_LEG_TIP, ModelPartBuilder.create().cuboid("main", -3.0F, -1.0F, -3.0F, 6, 24, 6, 226, 138), ModelTransform.pivot(0.0F, 20.0F, -1.0F));
        modelPartData10.addChild(EntityModelPartNames.RIGHT_FRONT_FOOT, ModelPartBuilder.create().cuboid("main", -4.0F, 0.0F, -12.0F, 8, 4, 16, 144, 104), ModelTransform.pivot(0.0F, 23.0F, 0.0F));
        ModelPartData modelPartData11 = modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, ModelPartBuilder.create().cuboid("main", -8.0F, -4.0F, -8.0F, 16, 32, 16, 0, 0), ModelTransform.pivot(-16.0F, 16.0F, 42.0F));
        ModelPartData modelPartData12 = modelPartData11.addChild(EntityModelPartNames.RIGHT_HIND_LEG_TIP, ModelPartBuilder.create().cuboid("main", -6.0F, -2.0F, 0.0F, 12, 32, 12, 196, 0), ModelTransform.pivot(0.0F, 32.0F, -4.0F));
        modelPartData12.addChild(EntityModelPartNames.RIGHT_HIND_FOOT, ModelPartBuilder.create().cuboid("main", -9.0F, 0.0F, -20.0F, 18, 6, 24, 112, 0), ModelTransform.pivot(0.0F, 31.0F, 4.0F));
        return TexturedModelData.of(modelData, 256, 256);
    }

    static {
        DRAGON_CUTOUT = RenderLayer.getEntityCutoutNoCull(TEXTURE);
        DRAGON_DECAL = RenderLayer.getEntityDecal(TEXTURE);
        DRAGON_EYES = RenderLayer.getEyes(EYE_TEXTURE);
        CRYSTAL_BEAM_LAYER = RenderLayer.getEntitySmoothCutout(CRYSTAL_BEAM_TEXTURE);
        HALF_SQRT_3 = (float)(Math.sqrt(3.0D) / 2.0D);
    }

    @Environment(EnvType.CLIENT)
    public static class DragonEntityModel extends EntityModel<ZombieEnderDragon>
    {
        private final ModelPart head;
        private final ModelPart neck;
        private final ModelPart jaw;
        private final ModelPart body;
        private final ModelPart leftWing;
        private final ModelPart leftWingTip;
        private final ModelPart leftFrontLeg;
        private final ModelPart leftFrontLegTip;
        private final ModelPart leftFrontFoot;
        private final ModelPart leftHindLeg;
        private final ModelPart leftHindLegTip;
        private final ModelPart leftHindFoot;
        private final ModelPart rightWing;
        private final ModelPart rightWingTip;
        private final ModelPart rightFrontLeg;
        private final ModelPart rightFrontLegTip;
        private final ModelPart rightFrontFoot;
        private final ModelPart rightHindLeg;
        private final ModelPart rightHindLegTip;
        private final ModelPart rightHindFoot;
        @Nullable
        private ZombieEnderDragon dragon;
        private float tickDelta;

        public DragonEntityModel(ModelPart modelPart) {
            this.head = modelPart.getChild(EntityModelPartNames.HEAD);
            this.jaw = this.head.getChild(EntityModelPartNames.JAW);
            this.neck = modelPart.getChild(EntityModelPartNames.NECK);
            this.body = modelPart.getChild(EntityModelPartNames.BODY);
            this.leftWing = modelPart.getChild(EntityModelPartNames.LEFT_WING);
            this.leftWingTip = this.leftWing.getChild(EntityModelPartNames.LEFT_WING_TIP);
            this.leftFrontLeg = modelPart.getChild(EntityModelPartNames.LEFT_FRONT_LEG);
            this.leftFrontLegTip = this.leftFrontLeg.getChild(EntityModelPartNames.LEFT_FRONT_LEG_TIP);
            this.leftFrontFoot = this.leftFrontLegTip.getChild(EntityModelPartNames.LEFT_FRONT_FOOT);
            this.leftHindLeg = modelPart.getChild(EntityModelPartNames.LEFT_HIND_LEG);
            this.leftHindLegTip = this.leftHindLeg.getChild(EntityModelPartNames.LEFT_HIND_LEG_TIP);
            this.leftHindFoot = this.leftHindLegTip.getChild(EntityModelPartNames.LEFT_HIND_FOOT);
            this.rightWing = modelPart.getChild(EntityModelPartNames.RIGHT_WING);
            this.rightWingTip = this.rightWing.getChild(EntityModelPartNames.RIGHT_WING_TIP);
            this.rightFrontLeg = modelPart.getChild(EntityModelPartNames.RIGHT_FRONT_LEG);
            this.rightFrontLegTip = this.rightFrontLeg.getChild(EntityModelPartNames.RIGHT_FRONT_LEG_TIP);
            this.rightFrontFoot = this.rightFrontLegTip.getChild(EntityModelPartNames.RIGHT_FRONT_FOOT);
            this.rightHindLeg = modelPart.getChild(EntityModelPartNames.RIGHT_HIND_LEG);
            this.rightHindLegTip = this.rightHindLeg.getChild(EntityModelPartNames.RIGHT_HIND_LEG_TIP);
            this.rightHindFoot = this.rightHindLegTip.getChild(EntityModelPartNames.RIGHT_HIND_FOOT);
        }

        public void animateModel(ZombieEnderDragon enderDragonEntity, float f, float g, float h) {
            this.dragon = enderDragonEntity;
            this.tickDelta = h;
        }

        public void setAngles(ZombieEnderDragon enderDragonEntity, float f, float g, float h, float i, float j) {
        }

        public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
            matrices.push();
            float f = MathHelper.lerp(this.tickDelta, this.dragon.prevWingPosition, this.dragon.wingPosition);
            this.jaw.pitch = (float)(Math.sin((double)(f * 6.2831855F)) + 1.0D) * 0.2F;
            float g = (float)(Math.sin((double)(f * 6.2831855F - 1.0F)) + 1.0D);
            g = (g * g + g * 2.0F) * 0.05F;
            matrices.translate(0.0D, (double)(g - 2.0F), -3.0D);
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(g * 2.0F));
            float h = 0.0F;
            float i = 20.0F;
            float j = -12.0F;
            float k = 1.5F;
            double[] ds = this.dragon.getSegmentProperties(6, this.tickDelta);
            float l = MathHelper.fwrapDegrees(this.dragon.getSegmentProperties(5, this.tickDelta)[0] - this.dragon.getSegmentProperties(10, this.tickDelta)[0]);
            float m = MathHelper.fwrapDegrees(this.dragon.getSegmentProperties(5, this.tickDelta)[0] + (double)(l / 2.0F));
            float n = f * 6.2831855F;

            float p;
            for(int o = 0; o < 5; ++o) {
                double[] es = this.dragon.getSegmentProperties(5 - o, this.tickDelta);
                p = (float)Math.cos((double)((float)o * 0.45F + n)) * 0.15F;
                this.neck.yaw = MathHelper.fwrapDegrees(es[0] - ds[0]) * 0.017453292F * 1.5F;
                this.neck.pitch = p + this.dragon.getChangeInNeckPitch(o, ds, es) * 0.017453292F * 1.5F * 5.0F;
                this.neck.roll = -MathHelper.fwrapDegrees(es[0] - (double)m) * 0.017453292F * 1.5F;
                this.neck.pivotY = i;
                this.neck.pivotZ = j;
                this.neck.pivotX = h;
                i = (float)((double)i + Math.sin((double)this.neck.pitch) * 10.0D);
                j = (float)((double)j - Math.cos((double)this.neck.yaw) * Math.cos((double)this.neck.pitch) * 10.0D);
                h = (float)((double)h - Math.sin((double)this.neck.yaw) * Math.cos((double)this.neck.pitch) * 10.0D);
                this.neck.render(matrices, vertices, light, overlay, 1.0F, 1.0F, 1.0F, alpha);
            }

            this.head.pivotY = i;
            this.head.pivotZ = j;
            this.head.pivotX = h;
            double[] o = this.dragon.getSegmentProperties(0, this.tickDelta);
            this.head.yaw = MathHelper.fwrapDegrees(o[0] - ds[0]) * 0.017453292F;
            this.head.pitch = MathHelper.fwrapDegrees((double)this.dragon.getChangeInNeckPitch(6, ds, o)) * 0.017453292F * 1.5F * 5.0F;
            this.head.roll = -MathHelper.fwrapDegrees(o[0] - (double)m) * 0.017453292F;
            this.head.render(matrices, vertices, light, overlay, 1.0F, 1.0F, 1.0F, alpha);
            matrices.push();
            matrices.translate(0.0D, 1.0D, 0.0D);
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(-l * 1.5F));
            matrices.translate(0.0D, -1.0D, 0.0D);
            this.body.roll = 0.0F;
            this.body.render(matrices, vertices, light, overlay, 1.0F, 1.0F, 1.0F, alpha);
            float es = f * 6.2831855F;
            this.leftWing.pitch = 0.125F - (float)Math.cos((double)es) * 0.2F;
            this.leftWing.yaw = -0.25F;
            this.leftWing.roll = -((float)(Math.sin((double)es) + 0.125D)) * 0.8F;
            this.leftWingTip.roll = (float)(Math.sin((double)(es + 2.0F)) + 0.5D) * 0.75F;
            this.rightWing.pitch = this.leftWing.pitch;
            this.rightWing.yaw = -this.leftWing.yaw;
            this.rightWing.roll = -this.leftWing.roll;
            this.rightWingTip.roll = -this.leftWingTip.roll;
            this.setLimbRotation(matrices, vertices, light, overlay, g, this.leftWing, this.leftFrontLeg, this.leftFrontLegTip, this.leftFrontFoot, this.leftHindLeg, this.leftHindLegTip, this.leftHindFoot, alpha);
            this.setLimbRotation(matrices, vertices, light, overlay, g, this.rightWing, this.rightFrontLeg, this.rightFrontLegTip, this.rightFrontFoot, this.rightHindLeg, this.rightHindLegTip, this.rightHindFoot, alpha);
            matrices.pop();
            p = -((float)Math.sin((double)(f * 6.2831855F))) * 0.0F;
            n = f * 6.2831855F;
            i = 10.0F;
            j = 60.0F;
            h = 0.0F;
            ds = this.dragon.getSegmentProperties(11, this.tickDelta);

            for(int q = 0; q < 12; ++q) {
                o = this.dragon.getSegmentProperties(12 + q, this.tickDelta);
                p = (float)((double)p + Math.sin((double)((float)q * 0.45F + n)) * 0.05000000074505806D);
                this.neck.yaw = (MathHelper.fwrapDegrees(o[0] - ds[0]) * 1.5F + 180.0F) * 0.017453292F;
                this.neck.pitch = p + (float)(o[1] - ds[1]) * 0.017453292F * 1.5F * 5.0F;
                this.neck.roll = MathHelper.fwrapDegrees(o[0] - (double)m) * 0.017453292F * 1.5F;
                this.neck.pivotY = i;
                this.neck.pivotZ = j;
                this.neck.pivotX = h;
                i = (float)((double)i + Math.sin((double)this.neck.pitch) * 10.0D);
                j = (float)((double)j - Math.cos((double)this.neck.yaw) * Math.cos((double)this.neck.pitch) * 10.0D);
                h = (float)((double)h - Math.sin((double)this.neck.yaw) * Math.cos((double)this.neck.pitch) * 10.0D);
                this.neck.render(matrices, vertices, light, overlay, 1.0F, 1.0F, 1.0F, alpha);
            }

            matrices.pop();
        }

        private void setLimbRotation(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float offset, ModelPart wing, ModelPart frontLeg, ModelPart frontLegTip, ModelPart frontFoot, ModelPart hindLeg, ModelPart hindLegTip, ModelPart hindFoot, float f) {
            hindLeg.pitch = 1.0F + offset * 0.1F;
            hindLegTip.pitch = 0.5F + offset * 0.1F;
            hindFoot.pitch = 0.75F + offset * 0.1F;
            frontLeg.pitch = 1.3F + offset * 0.1F;
            frontLegTip.pitch = -0.5F - offset * 0.1F;
            frontFoot.pitch = 0.75F + offset * 0.1F;
            wing.render(matrices, vertices, light, overlay, 1.0F, 1.0F, 1.0F, f);
            frontLeg.render(matrices, vertices, light, overlay, 1.0F, 1.0F, 1.0F, f);
            hindLeg.render(matrices, vertices, light, overlay, 1.0F, 1.0F, 1.0F, f);
        }
    }
}
